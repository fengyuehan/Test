package com.example.jetpack.ktpaging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.jetpack.R
import com.hi.dhl.jdatabinding.dowithTry

class GitHubAdapter:PagingDataAdapter<GitHubAccount,GitHubViewHolder>(GitHubAccount.diffCallback) {
    override fun onBindViewHolder(holder: GitHubViewHolder, position: Int) {
        dowithTry {
            val data = getItem(position)
            data?.let {
                holder.bindData(it,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycie_item_github,parent,false)
        return GitHubViewHolder(view)
    }
}