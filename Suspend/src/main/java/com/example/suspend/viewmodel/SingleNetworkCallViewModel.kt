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

class SingleNetworkCallViewModel(
        private val apiHelper: ApiHelper,
        private val dbHelper: DatabaseHelper
) :ViewModel() {
    val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUser()
    }

    /**
     * collectLatest与collect的区别
     * xxxLatest： 只关注流中最新的元素, 当新元素到达时, 如果前一个元素还没有处理完, 则取消它, 并且马上处理最新的元素. 有一组 xxxLatest 操作符,
     * 如 collectLatest, mapLatest , combineLatest 等, 用于处理这种情况.
     *
     */
    private fun fetchUser() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            apiHelper.getUser()
                    .flowOn(Dispatchers.Default)
                    .catch { e ->
                        users.postValue(Resource.error(e.toString(),null))
                    }
                    .collectLatest {
                        users.postValue(Resource.success(it))
                    }

        }
    }
}