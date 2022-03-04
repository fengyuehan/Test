package com.example.suspend.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suspend.Flow.data.ApiHelper
import com.example.suspend.Flow.db.DatabaseHelper
import com.example.suspend.Flow.db.User
import com.example.suspend.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RoomDBViewModel(private val apiHelper: ApiHelper,private val dbHelper: DatabaseHelper):ViewModel() {

    val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            dbHelper.getUser()
                    .flatMapConcat { userFromDb ->
                        if (userFromDb.isEmpty()){
                            return@flatMapConcat apiHelper.getUser()
                                    .map { apiUserList ->
                                        val userList = mutableListOf<User>()
                                        for (apiUser in apiUserList){
                                            val user = User(
                                                    apiUser.id,
                                                    apiUser.name,
                                                    apiUser.email,
                                                    apiUser.avatar
                                            )
                                            userList.add(user)
                                        }
                                        userList
                                    }
                                    .flatMapConcat { usersToInsertInDB->
                                        dbHelper.insertAll(usersToInsertInDB)
                                                .flatMapConcat {
                                                    flow {
                                                        emit(usersToInsertInDB)
                                                    }
                                                }
                                    }
                        }else{
                            return@flatMapConcat flow {
                                emit(userFromDb)
                            }
                        }

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