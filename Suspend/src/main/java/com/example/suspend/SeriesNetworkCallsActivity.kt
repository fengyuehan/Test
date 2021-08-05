package com.example.suspend

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suspend.Flow.adapter.ApiUserAdapter
import com.example.suspend.Flow.data.ApiHelperImpl
import com.example.suspend.Flow.data.RetrofitBuilder
import com.example.suspend.Flow.db.DatabaseBuilder
import com.example.suspend.Flow.db.DatabaseHelperImpl
import com.example.suspend.viewmodel.SeriesNetworkCallsViewModel
import kotlinx.android.synthetic.main.activity_recycler_view.*

class SeriesNetworkCallsActivity :AppCompatActivity() {
    private lateinit var apiUserAdapter: ApiUserAdapter
    private lateinit var seriesNetworkCallsViewModel: SeriesNetworkCallsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        initUI()
        initModel()
        initObserver()
    }

    private fun initObserver() {
        seriesNetworkCallsViewModel.users.observe(this, Observer {
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
                    Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initModel() {
        seriesNetworkCallsViewModel = ViewModelProviders.of(this,ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(this))
        )).get(SeriesNetworkCallsViewModel::class.java)
    }

    private fun initUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        apiUserAdapter = ApiUserAdapter(arrayListOf())
        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        recyclerView.context,
                        (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
        )
        recyclerView.adapter = apiUserAdapter
    }
}