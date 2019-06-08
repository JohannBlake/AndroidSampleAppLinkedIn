package com.linkedintools.ui.login

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.linkedintools.App
import com.linkedintools.R
import com.linkedintools.da.web.urls.LINKEDIN_LOGIN_PAGE
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment() {

    lateinit var mView: View
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_login, container, false)

        val webview = mView.webview
        val llLogin = mView.llLogin
        val btnLogin = mView.btnLogin

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.onLoginSuccess.observe(this, Observer {
            navigateToMainActivity()
        })

        viewModel.onError.observe(this, Observer {
            btnLogin.revertAnimation()

            val dialogBuilder = AlertDialog.Builder(activity!!)
            dialogBuilder.setMessage(it.toString())
                .setCancelable(false)
                .setPositiveButton(App.context.getString(R.string.ok), DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })

            val alert = dialogBuilder.create()
            alert.setTitle(App.context.getString(R.string.app_name))
            alert.show()
        })

        viewModel.onLoginNotNeeded.observe(this, Observer {
            // We navigate to the main activity here because the CircularProgressButton needs to be initialized even
            // though this activity is going to be destroyed. The app would crash if CircularProgressButton is not
            // displayed (this is a bug in CircularProgressButton). At the same time, we don't really want to display
            // the sign in button because we don't want to let the user sign in since they have already been
            // authorized. By setting the alpha to zero, the button is rendered in the layout but is not visible and
            // allows the button to properly initialize.
            // The one second delay allows the screen to appear like a splash screen.

            btnLogin.alpha = 0.0f
            val handler = Handler()
            handler.postDelayed({
                navigateToMainActivity()
            }, 1000)
        })

        viewModel.onHideLinkedInLoginWhileProcessing.observe(this, Observer {
            // Change the Login button to a loading indicator and display it until the login has completed.
            webview.visibility = View.GONE
            llLogin.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE
        })

        viewModel.onAuthenticateUser.observe(this, Observer {
            webview.loadUrl(LINKEDIN_LOGIN_PAGE)
            btnLogin.visibility = View.VISIBLE

            btnLogin.setOnClickListener {
                btnLogin.startAnimation {
                    // Let the animated progress indicator show for a second to give the user some indication that
                    // that something is happening. Without the delay, the webview will show immediately.
                    val handler = Handler()
                    handler.postDelayed({
                        llLogin.visibility = View.GONE
                        btnLogin.visibility = View.GONE
                        webview.visibility = View.VISIBLE
                    }, 1000)
                }
            }

            val webSettings = webview.settings
            webSettings.javaScriptEnabled = true

            webview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    viewModel.shouldOverrideUrlLoading(url)
                    return false //Allow WebView to load url. Returning true indicates WebView to NOT load the url;
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    viewModel.onPageFinished(url)
                }
            }
        })

        viewModel.login()

        return mView
    }

    fun navigateToMainActivity() {
        mView.findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        activity!!.finish()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
