package com.example.jetpack.ktpaging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.jetpack.R
import com.example.jetpack.databinding.ActivityKtPagingBinding
import kotlinx.android.synthetic.main.activity_kt_paging.*
import kotlinx.android.synthetic.main.activity_paging.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.anko.error

class KtPagingActivity:AppCompatActivity() {
    private var mainViewModel: MainViewModel? = null
    private var mBinding: ActivityKtPagingBinding? = null
    private val mAdapter by lazy { GitHubAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = MainViewModel(RepositoryFactory(GitHubService.create()).makeGutHubRepository())
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_kt_paging)
        mBinding?.apply {
            rv.adapter = mAdapter.withLoadStateFooter(footer = FooterAdapter(mAdapter))
            swipeRefresh.setOnRefreshListener { mAdapter.refresh() }
            lifecycleOwner = this@KtPagingActivity
        }

        mainViewModel!!.liveData.observe(this, Observer {
            mAdapter.submitData(lifecycle,it)
        })

        lifecycleScope.launch {
            mAdapter.loadStateFlow.collectLatest {
                state -> swipeRefresh.isRefreshing = state.refresh is LoadState.Loading
            }
        }

        mAdapter.addLoadStateListener {
            listener -> when(listener.refresh){
            is LoadState.Error -> {
                tvResult.setText(listener.refresh.toString())
                error{
                    listener.refresh.toString()
                }
            }
            is LoadState.Loading -> {
                error{
                    listener.refresh.toString()
                }
            }
            is LoadState.NotLoading -> { // 当前未加载
                error { listener.refresh.toString() }
            }
        }
        }
    }

}