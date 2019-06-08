package com.linkedintools.ui.main.fragments.connections

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.linkedintools.R
import com.linkedintools.di.components.DaggerConnectionsFragmentComponent
import com.linkedintools.model.LinkedInConnection
import com.linkedintools.ui.OnFragmentInteractionListener
import com.linkedintools.ui.main.MainActivity
import com.linkedintools.ui.main.fragments.connections.adapter.ClickListener
import com.linkedintools.ui.main.fragments.connections.adapter.ConnectionsAdapter
import javax.inject.Inject

class ConnectionsFragment : Fragment() {

    init {
        DaggerConnectionsFragmentComponent
            .builder()
            .build()
            .inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ConnectionsViewModelFactory

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var footer: LinearLayout

    private val clickListener: ClickListener = this::onListItemClicked
    private val recyclerViewAdapter = ConnectionsAdapter(clickListener)


    private lateinit var viewModel: ConnectionsViewModel

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_connections, container, false)

        swipeRefresh = view.findViewById(R.id.swipe_refresh_layout)
        recyclerView = view.findViewById(R.id.rv_connections)
        footer = view.findViewById(R.id.ll_footer)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConnectionsViewModel::class.java)

        viewModel.connectionsByFirstName.observe(this, Observer { connections ->
            connections?.let { render(connections) }
        })

        viewModel.onConnectionsCountRetrieved.observe(this, Observer { count ->
            var title = getString(R.string.connections)

            if (count > 0)
                title += " (" + "%,d".format(count) + ")"

            (activity as MainActivity).getSupportActionBar()?.title = title
        })


        viewModel.onConnectionsRetrievalStarted.observe(this, Observer { e  ->
            footer.visibility = View.VISIBLE
        })

        viewModel.onConnectionsRetrievalCompleted.observe(this, Observer { e  ->
            footer.visibility = View.GONE
            swipeRefresh.setRefreshing(false)
        })

        swipeRefresh.setOnRefreshListener {
            viewModel.getConnections()
        }

        setupRecyclerView()

        viewModel.notifyIfRetrievingConnections()
        viewModel.getConnectionsCount()
        viewModel.getConnectionsIfNoneExist()

        return view
    }


    private fun render(pageList: PagedList<LinkedInConnection>) {
        recyclerViewAdapter.submitList(pageList)

        if (pageList.isEmpty()) {
            recyclerView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun onListItemClicked(connection: LinkedInConnection) {

    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = recyclerViewAdapter
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
