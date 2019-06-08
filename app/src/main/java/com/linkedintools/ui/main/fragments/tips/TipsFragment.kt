package com.linkedintools.ui.main.fragments.tips

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.linkedintools.R
import com.linkedintools.di.components.DaggerTipsFragmentComponent
import com.linkedintools.ui.OnFragmentInteractionListener
import javax.inject.Inject

class TipsFragment : Fragment() {

    init {
        DaggerTipsFragmentComponent
            .builder()
            .build()
            .inject(this)
    }

    @Inject
    lateinit var viewModelFactory: TipsViewModelFactory

    private lateinit var viewModel: TipsViewModel
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tips, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TipsViewModel::class.java)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnFragmentInteractionListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
