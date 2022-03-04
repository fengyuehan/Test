package com.example.jetpack.ktpaging

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.example.jetpack.databinding.RecycieItemNetworkStateBinding
import com.hi.dhl.jdatabinding.DataBindingViewHolder

class NetworkStateItemViewHolder(view: View,private val retryCallback:() -> Unit):DataBindingViewHolder<LoadState>(view) {

    val mBinding:RecycieItemNetworkStateBinding by viewHolderBinding(view)
    override fun bindData(data: LoadState, position: Int) {
        mBinding.apply {
            progressBar.isVisible = data is LoadState.Loading
            retryButton.isVisible = data is LoadState.Error
            retryButton.setOnClickListener { retryCallback }
            errorMsg.isVisible = !(data as LoadState.Error).error.message.isNullOrBlank()
            errorMsg.text = (data as LoadState.Error).error.message
            executePendingBindings()
        }
    }
}

