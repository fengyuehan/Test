package com.example.suspend

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suspend.Flow.adapter.ApiUserAdapter
import com.example.suspend.Flow.data.ApiHelperImpl
import com.example.suspend.Flow.data.RetrofitBuilder
import com.example.suspend.Flow.db.DatabaseBuilder
import com.example.suspend.Flow.db.DatabaseHelperImpl
import com.example.suspend.viewmodel.ParallelNetworkCallsViewModel
import kotlinx.android.synthetic.main.activity_recycler_view.*

class ParallelNetworkCallsActivity :AppCompatActivity() {
    private lateinit var apiUserAdapter: ApiUserAdapter
    private lateinit var parallelNetworkCallsViewModel: ParallelNetworkCallsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initUI()
        initModel()
        initObserver()
    }

    private fun initObserver() {
        parallelNetworkCallsViewModel.users.observe(this, Observer {
            when(it.status){
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    it.data?.let {
                        list -> apiUserAdapter.addData(list)
                    }
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this,it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initModel() {
        parallelNetworkCallsViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService),DatabaseHelperImpl(DatabaseBuilder.getInstance(this)))
        ).get(ParallelNetworkCallsViewModel::class.java)
    }

    private fun initUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        apiUserAdapter = ApiUserAdapter(arrayListOf())
        recyclerView.addItemDecoration(DividerItemDecoration(
                this,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
        ))
        recyclerView.adapter = apiUserAdapter
    }
}