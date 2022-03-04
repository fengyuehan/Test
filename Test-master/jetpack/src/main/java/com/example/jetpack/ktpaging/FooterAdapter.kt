package com.example.jetpack.ktpaging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.jetpack.R

class FooterAdapter(val adapter:GitHubAdapter) :LoadStateAdapter<NetworkStateItemViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindData(loadState,0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): NetworkStateItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycie_item_network_state,parent,false)
        return NetworkStateItemViewHolder(view){
            adapter.retry()
        }
    }
}