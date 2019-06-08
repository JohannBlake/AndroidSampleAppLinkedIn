package com.linkedintools.ui.main.fragments.connections.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.linkedintools.model.LinkedInConnection
import com.linkedintools.ui.main.fragments.connections.ConnectionsViewHolder

typealias ClickListener = (LinkedInConnection) -> Unit

class ConnectionsAdapter(
    private val clickListener: ClickListener
) : PagedListAdapter<LinkedInConnection, ConnectionsViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ConnectionsViewHolder, position: Int) {
        val linkedInConnection = getItem(position)

        with(holder) {
            bindTo(linkedInConnection)
            linkedInConnection?.let {
                itemView.setOnClickListener {
                    clickListener(linkedInConnection)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectionsViewHolder =
        ConnectionsViewHolder(parent)

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         */
        private val diffCallback = object : DiffUtil.ItemCallback<LinkedInConnection>() {
            override fun areItemsTheSame(oldItem: LinkedInConnection, newItem: LinkedInConnection): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: LinkedInConnection, newItem: LinkedInConnection): Boolean = oldItem == newItem
        }
    }
}