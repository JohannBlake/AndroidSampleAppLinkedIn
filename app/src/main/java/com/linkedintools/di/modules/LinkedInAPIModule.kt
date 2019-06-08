package com.linkedintools.di.modules

import com.linkedintools.da.local.SharedPrefs
import com.linkedintools.da.web.LinkedInAPI
import com.linkedintools.da.web.urls.LINKEDIN_HOME_PAGE
import com.linkedintools.da.web.utils.LinkedInConverters
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException

/**
 * Provides a Retrofit for accessing LinkedIn content.
 */
@Module(includes = [SharedPrefsModule::class])
class LinkedInAPIModule {

    @Provides
    @Reusable
    fun provideRetrofitForLinkedIn(): LinkedInAPI {
        return Retrofit.Builder()
            .baseUrl(LINKEDIN_HOME_PAGE)
            .addConverterFactory(LinkedInConverters())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(initializeRetrofit())
            .build()
            .create(LinkedInAPI::class.java)
    }

    private fun initializeRetrofit() : OkHttpClient {

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val sharedPrefs: SharedPrefs = SharedPrefs()

                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                    .header("cookie", sharedPrefs.cookie)
                    .header("csrf-token", sharedPrefs.csrfToken)
                    .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")

                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })

        val client = httpClient.build()
        return client
    }
}