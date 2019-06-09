# Android Sample App Demonstrating Best Practices (Kotlin)

This is a sample application that implements the best current design practices in developing Android applications and includes the following architectural components and patterns:

* Clean architecture with layers separated by UI, ViewModel, Business Objects, Repository and Service
* MVVM (Model-View-ViewModel) using the ViewModel class
* Dagger 2 for dependency injection
* Reactive programming using RxJava
* Navigation controller for navigating between activities/fragments
* Data binding UI elements to data models
* Retrofit for accessing backend APIs
* Custom data converters for processing data retrieved with Retrofit
* LiveData for communicating data and events between the UI and ViewModel
* RxJava-based event bus to support publishing and subscribing to events in non-connected areas throughout the app
* Room database storage with automatic UI updates
* RecyclerView and Paging component bound to Room database
* High performance image loading and caching using Glide
* User authentication using a WebView
* Navigation drawer for navigating to different fragments

## About the App

The app will retrieve a list of all your connections from your LinkedIn account and display them in a recyclerview list with each list item showing the name of the person, their photo and their current role/title. The list is stored in a Room database.

When first launched, you are presented with a sign in screen. When you click on the sign in button, you will be presented with the sign in screen from LinkedIn. After you sign in, you are taken to the app's main screen where your connections will be automatically downloaded in batches of 40.

The navigation drawer has a few other items including Groups, Messages and Tips. Only bare bone fragments are implemented for these items and clicking on them will only show some text on the fragment to indicate the fragment you have selected.

The *Sign out* item will cause you to return back to the sign in screen.

If you restart the app after signing in, your list of connections is automatically retrieved from the Room database.

The app employs a service for retrieving LinkedIn connections in the background (as a foreground service) and stores the data in the Room database in batches of 40. Because the recyclerview is bound to the Room database, the UI is updated immediately and automaticly whenever data is stored allowing for an asynchronous operation to take place. You can also pull down on the list (when the list is scrolled to the top) which will cause the service to retrieve all the LinkedIn connections again and update the list with any new connections it finds.

The app also demonstrates how to gracefully recover from a network exception. If you disable your network while your LinkedIn connections are being retrieved, up to 5 atttempts will be made to retry using exponential back off, after which an error message is shown.

You can download the apk file from the project's root and try it out without the need to compile the code. The filename is app-debug.apk


## Architecture

The app was designed using the architecture guidelines as recommended by Google at:

[https://developer.android.com/jetpack/docs/guide](https://developer.android.com/jetpack/docs/guide)

The architecture however has been slightly modified to include a layer referred to as the *"business layer"*, or as I like to refer to it, as the *"BO layer"*. The BO layer consists of those classes that provide the actual business logic that your app uses. Some developers refer to these as "use cases" or "business rules". The BO layer sits between your ViewModel layer and the data access layer (a.k.a. the "DA layer"). This is a block diagram of the architecture:

![https://github.com/JohannBlake/AndroidSampleAppLinkedIn/blob/master/images/android_architecture.png](https://github.com/JohannBlake/AndroidSampleAppLinkedIn/blob/master/images/android_architecture.png)
<br><br>
As a general rule, one layer can make calls to functions in another layer but not in reverse. For example, an activity can call functions in a ViewModel, but the ViewModel cannot call functions in the activity. In fact, the ViewModel has no knowledge of activities or fragments. In turn, a ViewModel can call functions in business objects but business objects cannot make calls to ViewModels. Business objects have no knowledge about ViewModels. There are however exceptions to this with services being one of them. Communication between services and the repository or business objects can be in both directions although it is strongly recommended to usually confine this two-way communication between the service and business objects and not include the repository.

When you allow one layer to bypass another layer to access another layer, you run the risk of bypassing centralized coding functionality that can provide checks and balances that could capture potential problems. For example, if the ViewModel directly accessed the repository without going through a business object, it is potentially possible that it could access data that it should not or display data in a way that the business object would otherwise alter or prevent. 
<br><br>

### UI Layer

Activities and fragments make up your UI layer. Code written here is essentially nothing more than glue that binds the UI components (TextViews, Buttons, etc) to your ViewModel to display stuff. And it also relays user input to the ViewModel. If you've properly coded your UI, you should rarely see an "if" statement. That may take some getting used to if you've never developed an MVVM app before. In reality, you can apply this technique to any architecture. It's not limited to the MVVM model. This demo app does actually use one "if" statement in the ConnectionsFragment but even this could have been replaced with data binding. But the amount of code used to add the data binding for this one case just didn't seem worth it.
<br><br>

### ViewModel Layer

The ViewModel layer acts like glue between the UI and BO layers. It's priimarily meant to cache the state of UI data whenever configuration changes take place such as a device orientation change. If an activity or fragment is destroyed and then recreated, the instance of the ViewModel that was used with the previous activity (before it was recreated) is reused and avoides the need to reload data from the backend or database. Where you can see the benefit in this demo app is when you scroll the list of connections to some item and then perform a device orientation change. Not only does the ViewModel retain the data for the list but even the position so that it is restored.

The Google docs state that the lifecycle of a ViewModel is tied directly to the lifecycle of the activity/fragment and when these are destroyed, the ViewModel is also destroyed. But this is not true and I'm not sure why this continues to be stated in the docs. You can place a breakpoint in the onDestroy function of an activity, then rotate the device to initiate a device change. The onDestroy will get called but the onCleared function in the ViewModel does not get called. The ViewModel does in fact outlive the life of an activity, and this can be seen in this app.

A situation where onCleared does get called is when you decide to handle configuration changes yourself using this:

```kotlin
override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
}
```
and adding this to your manifest for the activity:

```
android:configChanges="keyboardHidden|orientation|screenSize"
```
When you add this, onCleared will get called immediately after the activity's onDestroy is called. Of course this is bad since the whole point of the ViewModel is to cache your data and avoid retrieving it from its sources. So if your in the habit of using onConfigurationChanged, you probably will have no need for ViewModels and should just use the MVP architecture instead. Due to the complexity of handling configuraton changes with onConfigurationChanged, Google recommends that you avoid using it. See:

[https://developer.android.com/guide/topics/resources/runtime-changes.html](https://developer.android.com/guide/topics/resources/runtime-changes.html)

ViewModels should use LiveData when communicating data back to the UI layer. This is because LiveData has activity/fragment lifecycle awareness. An observable does not, although there are Rx libaries available that will allow you to use observables instead of LiveData. One typical pattern seen in apps is for the business object to return an observable to the ViewModel and then use LiveData to communicate this data back to the UI. The ConnectionsViewModel in this app does this when calling getConnectionsCount.
<br><br>

### Business Layer

Having seen many Android apps, I am still struck by how many developers still write business rules inside the ViewModel and let the ViewModel directly access the data layer. Even the block diagram of Google's recommended architecture shows no business logic layer and has the ViewModel communicating with the repository. This is really bad. Why? Because the ViewModel is really nothing more than glue and it should be used to bind your UI components to your business objects.

Regardless what kind of architecture you use, including MVP, your business components should be totally independent of any of the layers that need to use the business rules. They should be agnostic of any operating system or platform.

A lot of times, your ViewModel will just need to obtain some data from the repository and display it. And there is no need for a function in a business object to perform any special  pre-processing before retrieving the data nor any post processing after the data has been retrieved. The business object function just does a simple call to the repository and returns the data to the ViewModel. You may be tempted under these conditions to bypass the need for the business object and have the ViewModel directly access the repository. But you should avoid this. Always remain consistent in how you use layers.
<br><br>

### Repository Layer

The repository layer actually consists of several sub layers which includes access to shared preferences, files and databases. The repository is your centralized entry point when accessing data. It should be defined as a singleton and injected into your Application class so that it remains accessible throughout the lifetime of your app. The primary purpose of the repository is to forward calls from the business layer or service to the data sources responsible for handling the data. In many cases, observables are returned to the business object or service and not the actual data. The repository should not in any way process the data it retrieves from a data source. The processing of data for storage or retrieval should be done by the data source objects that handle shared preferences, files, and databases.
<br><br>

### Service Layer

If your app requires a service, it should only run for as long as it needs to process work and terminate immediately after the work has completed. This demo app illustrates this. Connections are retrieved from LinkedIn in the service that is running as a foreground service. This requires that a notification be displayed on the Android status bar. As soon as the connections have been retrieved, the service is terminated and the notification is removed. This will save on the device's battery consumption. Like the repository, it too should be accessible from your Application class. In this demo app, it is accessed indirectly through a utility class that manages the lifecycle of the service. Because a service can only be created by the OS, just like activities are only created by the OS, it is not possible to inject an instance of a service into the app using dependency injection.

It should also be noted that other layers cannot reliably know when the service is started or terminated by using APIs to check for their presence. Concurrency issues can arise when a service is started and stopped. For this reason, the service communicates its state of being either running or terminating with the utility class which stores this state as a boolean flag.
<br><br>

### Models

This is not a layer. These are data classes that are used to hold data used throughout the app. As a data class, it should only be used to hold data and not have any functions other than primitive ones like toString.
<br><br>

### RxJava-Based Event Bus

There are times when sections in your app need to get notified about certain events from other locations in your app. It could be that place where you want to get notified has no connection to the source where that notification is generated. While you could use RxJava to subscribe to an observable that is returned from some layer, this can at times be very awkward if the observable is created on some sub layer and needs to be returned up the hierarchy. In essence, you end up repeating the callback hell that RxJava is designed to avoid. And then there's the issue where you don't want to have some tight coupling between two unrelated portions of your app. For example, in a GPS app, there may be many diverse areas that would like to be notified whenever a GPS location is obtained. This is where an event bus comes into use. Of course, you can use alternative solutions such as broadcast receivers. But in keeping with the growing trend in reactive programming, this app employs the use of an event bus that is built using RxJava.

The RxJava-based event bus allows you to easily created publishers and subscribers. And instead of publishing just the data that subscribers are interested in, the data is wrapped in an observable allowing the subscriber to handle the published data in a more reactive fashion.

Publishing an event looks like this:

```kotlin
App.context.bus.onConnectionsRetrievalStarted().onNext(Unit)
```
And subscribing to one looks like this:

```kotlin
App.context.bus.onConnectionsRetrievalStarted().subscribe {
  // Do something
}
```
<br><br>
### MIT License

```
    The MIT License (MIT)

    Copyright (c) 2019 Johann Blake

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
```