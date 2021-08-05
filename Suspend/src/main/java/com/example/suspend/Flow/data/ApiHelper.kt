package com.example.suspend.Flow.data

import com.example.suspend.Flow.model.ApiUser
import kotlinx.coroutines.flow.Flow


interface ApiHelper {
    fun getUser(): Flow<List<ApiUser>>

    fun getMoreUser() :Flow<List<ApiUser>>

    fun getUserWithError() :Flow<List<ApiUser>>


}