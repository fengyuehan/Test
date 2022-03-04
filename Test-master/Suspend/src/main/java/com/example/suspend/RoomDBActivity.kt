package com.example.suspend

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suspend.Flow.adapter.UserAdapter
import com.example.suspend.Flow.data.ApiHelperImpl
import com.example.suspend.Flow.data.RetrofitBuilder
import com.example.suspend.Flow.db.DatabaseBuilder
import com.example.suspend.Flow.db.DatabaseHelperImpl
import com.example.suspend.Flow.db.User
import com.example.suspend.viewmodel.RoomDBViewModel
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RoomDBActivity :AppCompatActivity() {
    private lateinit var viewModel: RoomDBViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
                UserAdapter(
                        arrayListOf()
                )
        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        recyclerView.context,
                        (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(
                        ApiHelperImpl(RetrofitBuilder.apiService),
                        DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
                )
        ).get(RoomDBViewModel::class.java)
    }
}