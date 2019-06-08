package com.linkedintools.ui.main.fragments.connections.adapter

import androidx.recyclerview.widget.DiffUtil
import com.linkedintools.model.LinkedInConnection

class ConnectionDiffCallback : DiffUtil.ItemCallback<LinkedInConnection>() {
    override fun areItemsTheSame(oldItem: LinkedInConnection, newItem: LinkedInConnection): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LinkedInConnection, newItem: LinkedInConnection): Boolean {
        return oldItem == newItem
    }
}