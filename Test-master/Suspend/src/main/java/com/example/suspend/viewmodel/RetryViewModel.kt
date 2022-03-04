package com.example.suspend.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suspend.Flow.data.ApiHelper
import com.example.suspend.Flow.db.DatabaseHelper
import com.example.suspend.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class RetryViewModel(
        private val apiHelper: ApiHelper,
        private val databaseHelper: DatabaseHelper
):ViewModel() {
    val status = MutableLiveData<Resource<String>>()

    fun startTask(){
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            doLongRunningTask()
                    .flowOn(Dispatchers.Default)
                    .retry(2){
                        cause ->
                        if (cause is IOException){
                            delay(2000)
                            return@retry true
                        }else{
                            return@retry false
                        }
                    }
                    .catch {
                        e -> status.postValue(Resource.error(e.message,null))
                    }
                    .collectLatest {
                        status.postValue(Resource.success("Task Completed"))
                    }
        }
    }

    private fun doLongRunningTask(): Flow<Int> {
        return flow {
            // your code for doing a long running task
            // Added delay, random number, and exception to simulate

            delay(2000)

            val randomNumber = (0..2).random()

            if (randomNumber == 0) {
                throw IOException()
            } else if (randomNumber == 1) {
                throw IndexOutOfBoundsException()
            }

            delay(2000)
            emit(0)
        }
    }
}