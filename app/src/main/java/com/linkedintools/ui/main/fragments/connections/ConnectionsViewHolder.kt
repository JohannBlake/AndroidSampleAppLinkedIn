package com.linkedintools.ui.main.fragments.connections

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.linkedintools.App
import com.linkedintools.R
import com.linkedintools.model.LinkedInConnection
import com.linkedintools.utils.StringUtils

class ConnectionsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.connection_item, parent, false)
) {
    private val ivPhoto = itemView.findViewById<ImageView>(R.id.iv_photo)
    private val tvName = itemView.findViewById<TextView>(R.id.tv_name)
    private val tvOccupation = itemView.findViewById<TextView>(R.id.tv_occupation)
    private var connection: LinkedInConnection? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(con: LinkedInConnection?) {
        this.connection = con

        Glide.with(App.context)
            .load(con?.photoUrl)
            //.apply(RequestOptions.overrideOf(100, 100))
            .override(100, 100)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_person)
            .into(ivPhoto);

        tvName.text = StringUtils.concat(con?.firstName, con?.lastName, " ")
        tvOccupation.text = con?.occupation
    }
}