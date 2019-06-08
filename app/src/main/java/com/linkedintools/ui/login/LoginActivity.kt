package com.linkedintools.ui.login

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.linkedintools.R
import com.linkedintools.ui.OnFragmentInteractionListener


class LoginActivity : AppCompatActivity(), OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onFragmentInteraction(uri: Uri) {
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}
