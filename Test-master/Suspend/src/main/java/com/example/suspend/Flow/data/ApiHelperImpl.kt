package com.example.suspend.Flow.data

import com.example.suspend.Flow.model.ApiUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService):ApiHelper {
    override fun getUser(): Flow<List<ApiUser>> {
        return flow { emit(apiService.getUsers()) }
    }

    override fun getMoreUser(): Flow<List<ApiUser>> {
        return flow { emit(apiService.getMoreUsers()) }
    }

    override fun getUserWithError(): Flow<List<ApiUser>> {
        return flow { emit(apiService.getUsersWithError()) }
    }
}