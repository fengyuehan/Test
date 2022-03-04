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
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SeriesNetworkCallsViewModel(
        private val apiHelper: ApiHelper,
        private val dbHelper: DatabaseHelper
) :ViewModel(){
    val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchData()
    }

    /**
     * flatMapConcat:连接多个流, 使之扁平化, 它等待每一个内嵌的流结束后, 才会去 collect 下一个内嵌流.
     * flatMapMerge: 与 flatMapConcat 一样都是扁平化流, 不同的是, 它可以并发地(可以通过concurrency参数指定并发数) collect 多个流, 并且一旦其中一个有发射出元素时, 它都马上转发给下游.
     * flatMapLatest:flatMapLatest 只对最新的元素感兴趣, 当有新元素到达时, 它会取消当前的处理工作, 转而处理最新的元素. 所不同的是, 这里的元素指内嵌的流.
     * flowOn:线程的切换
     */
    private fun fetchData() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            val allUserFromApi = mutableListOf<ApiUser>()
            apiHelper.getUser()
                    .flatMapConcat { userFromApi ->
                        allUserFromApi.addAll(userFromApi)
                        apiHelper.getMoreUser()
                    }
                    .flowOn(Dispatchers.Default)
                    .catch {
                        e -> users.postValue(Resource.error(e.message,null))
                    }
                    .collectLatest {
                        moreUserFromApi -> allUserFromApi.addAll(moreUserFromApi)
                        users.postValue(Resource.success(allUserFromApi))
                    }
        }
    }
}