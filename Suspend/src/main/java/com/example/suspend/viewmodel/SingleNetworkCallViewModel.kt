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
    /**
     * livedata的优点：
     * 1、避免泄露：当 lifecycleOwner 进入 DESTROYED 时，会自动删除 Observer
     * 2、节省资源：当 lifecycleOwner 进入 STARTED 时才开始接受数据，避免 UI 处于后台时的无效计算。
     * 而flow却没有上面两种功能，所以提供了lifecycleScope解决内存泄露问题，虽然解决了内存泄漏问题， 但是 lifecycleScope.launch 会立即启动协程，之后一直运行直到协程销毁，无法像 LiveData 仅当 UI 处于前台才执行，对资源的浪费比较大。
     * 因此，lifecycle-runtime-ktx 又为我们提供了 LaunchWhenStarted  和 LaunchWhenResumed
     *
     * 但对于 launchWhenX 来说， 当 lifecycleOwner 离开 X 状态时，协程只是挂起协程而非销毁，如果用这个协程来订阅 Flow，就意味着虽然 Flow 的收集暂停了，
     * 但是上游的处理仍在继续，资源浪费的问题解决地不够彻底。
     *
     * 使用 repeatOnLifecycle 可以弥补上述 launchWhenX 对协程仅挂起而不销毁的弊端。
     *
     * onCreate{
     *      viewLifecycleOwner.lifecycleScope.launch {
     *          viewLifecycleOwner.lifecycle.repeatOnLifecycle(STARTED) {
     *              viewMode.stateFlow.collect { ... }
     *          }
     *      }
     * }
     *
     * 当我们只有一个 Flow 需要收集时，可以使用 flowWithLifecycle 这样一个 Flow 操作符的形式来简化代码
     *
     * lifecycleScope.launch {
     *      viewMode.stateFlow
     *              .flowWithLifecycle(this, Lifecycle.State.STARTED)
     *              .collect { ... }
     * }
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