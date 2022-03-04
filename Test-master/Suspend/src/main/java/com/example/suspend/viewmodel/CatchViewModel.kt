package com.example.suspend.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suspend.Flow.data.ApiHelper
import com.example.suspend.Flow.db.DatabaseHelper
import com.example.suspend.Flow.model.ApiUser
import com.example.suspend.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CatchViewModel(private val apiHelper: ApiHelper,databaseHelper: DatabaseHelper):ViewModel() {
    val users = MutableLiveData<Resource<List<ApiUser>>>()
    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            apiHelper.getUserWithError()
                    .flowOn(Dispatchers.Default)
                    .catch {
                        e -> users.postValue(Resource.error(e.message,null))
                    }
                    .collectLatest {
                        users.postValue(Resource.success(it))
                    }
        }
    }
}