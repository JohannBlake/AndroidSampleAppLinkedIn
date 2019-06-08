package com.linkedintools.ui.main

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.linkedintools.R
import com.linkedintools.databinding.NavHeaderMainBinding
import com.linkedintools.di.components.DaggerMainActivityComponent
import com.linkedintools.ui.OnFragmentInteractionListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    lateinit var viewModel: MainActivityViewModel

    init {
        DaggerMainActivityComponent
            .builder()
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setTitle(getString(R.string.connections))

        val viewHeader = nav_view.getHeaderView(0)
        val navViewHeaderBinding: NavHeaderMainBinding = NavHeaderMainBinding.bind(viewHeader)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        val linkedInUserSettings = viewModel.getLinkedInUserSettings()
        navViewHeaderBinding.user = linkedInUserSettings

        val imageViewPhoto = viewHeader.findViewById<ImageView>(R.id.imageview_photo)
        Glide.with(this).load(linkedInUserSettings.photoUrl).apply(RequestOptions.circleCropTransform()).into(imageViewPhoto);


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        navView.getMenu().getItem(0).setChecked(true);

        viewModel.onFirstAppUsage.observe(this, Observer {
            // Open up the navigation drawer. NOTE: This must be done after the navigation view has been completely initialized, otherwise the
            // hamburger menu ends up getting replaced with the Back arrow.
            //drawerLayout.openDrawer(Gravity.LEFT)
            viewModel.firstUsageInitialized()
        })

        viewModel.notifyIfFirstAppUsage()
    }

override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
}

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val navController = nav_host_fragment.findNavController()
        navController.popBackStack()

        when (item.itemId) {
            R.id.nav_connections -> {
                supportActionBar?.title = getString(R.string.connections);
                navController.navigate(R.id.connections_fragment)
            }
            R.id.nav_groups -> {
                supportActionBar?.title = getString(R.string.groups);
                navController.navigate(R.id.groups_fragment)
            }
            R.id.nav_messages -> {
                supportActionBar?.title = getString(R.string.messages);
                navController.navigate(R.id.messages_fragment)
            }
            R.id.nav_tips -> {
                supportActionBar?.title = getString(R.string.tips);
                navController.navigate(R.id.tips_fragment)
            }
            R.id.nav_sign_out -> {
                viewModel.signOut()
                navController.navigate(R.id.action_mainActivity_to_loginActivity)
                finish()
            }
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onFragmentInteraction(uri: Uri) {
    }
}
