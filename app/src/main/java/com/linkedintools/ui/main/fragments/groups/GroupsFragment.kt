package com.linkedintools.ui.main.fragments.groups

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.linkedintools.R
import com.linkedintools.di.components.DaggerGroupsFragmentComponent
import com.linkedintools.ui.OnFragmentInteractionListener
import javax.inject.Inject

class GroupsFragment : Fragment() {

    init {
        DaggerGroupsFragmentComponent
            .builder()
            .build()
            .inject(this)
    }

    @Inject
    lateinit var viewModelFactory: GroupsViewModelFactory

    private lateinit var viewModel: GroupsViewModel
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_groups, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GroupsViewModel::class.java)

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
