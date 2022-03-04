package com.example.suspend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suspend.Flow.data.ApiHelper
import com.example.suspend.Flow.db.DatabaseHelper
import com.example.suspend.viewmodel.*

class ViewModelFactory(private val apiHelper: ApiHelper,private val dbHelper: DatabaseHelper) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleNetworkCallViewModel::class.java)){
            return SingleNetworkCallViewModel(apiHelper,dbHelper) as T
        }
        if(modelClass.isAssignableFrom(SeriesNetworkCallsViewModel::class.java)){
            return SeriesNetworkCallsViewModel(apiHelper,dbHelper) as T
        }
        if (modelClass.isAssignableFrom(ParallelNetworkCallsViewModel::class.java)){
            return ParallelNetworkCallsViewModel(apiHelper,dbHelper) as T
        }
        if (modelClass.isAssignableFrom(RoomDBViewModel::class.java)){
            return RoomDBViewModel(apiHelper,dbHelper) as T
        }
        if (modelClass.isAssignableFrom(CatchViewModel::class.java)){
            return CatchViewModel(apiHelper,dbHelper) as T
        }
        if (modelClass.isAssignableFrom(RetryViewModel::class.java)){
            return RetryViewModel(apiHelper,dbHelper) as T
        }
        if (modelClass.isAssignableFrom(RetryWhenViewModel::class.java)){
            return RetryWhenViewModel(apiHelper,dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }


}