package com.linkedintools.ui.main.fragments.messages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.linkedintools.R
import com.linkedintools.di.components.DaggerMessagesFragmentComponent
import com.linkedintools.ui.OnFragmentInteractionListener
import javax.inject.Inject

class MessagesFragment : Fragment() {

    init {
        DaggerMessagesFragmentComponent
            .builder()
            .build()
            .inject(this)
    }

    @Inject
    lateinit var viewModelFactory: MessagesViewModelFactory

    private lateinit var viewModel: MessagesViewModel
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_messages, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MessagesViewModel::class.java)

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
