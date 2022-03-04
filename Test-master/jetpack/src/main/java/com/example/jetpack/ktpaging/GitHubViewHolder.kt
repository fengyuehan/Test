package com.example.jetpack.ktpaging

import android.view.View
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.jetpack.R
import com.example.jetpack.databinding.RecycieItemGithubBinding
import com.hi.dhl.jdatabinding.DataBindingViewHolder

class GitHubViewHolder(view:View):DataBindingViewHolder<GitHubAccount>(view) {
    private val mBinding:RecycieItemGithubBinding by viewHolderBinding(view)
    override fun bindData(data: GitHubAccount, position: Int) {
        mBinding.apply {
            githubAccount = data
            avatar.load(data.avatar_url){
                crossfade(true)
                placeholder(R.mipmap.ic_launcher)
                transformations(CircleCropTransformation())
            }
            executePendingBindings()
        }
    }

}