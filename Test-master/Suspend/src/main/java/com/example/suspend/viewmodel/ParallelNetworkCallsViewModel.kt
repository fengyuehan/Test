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
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class ParallelNetworkCallsViewModel(
        private val apiHelper :ApiHelper,
        private val dbHelper: DatabaseHelper
):ViewModel() {
    val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            apiHelper.getUser()
                    .zip(apiHelper.getMoreUser()){
                        userFromAPi,moreUserFromApi ->
                        val allUserFromApi = mutableListOf<ApiUser>()
                        allUserFromApi.addAll(userFromAPi)
                        allUserFromApi.addAll(moreUserFromApi)
                        return@zip allUserFromApi
                    }
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