package com.example.suspend.Flow.db

import kotlinx.coroutines.flow.Flow


interface DatabaseHelper {
    fun getUser() : Flow<List<User>>

    fun insertAll(user: List<User>):Flow<Unit>

}