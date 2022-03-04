package com.example.suspend.Flow.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DatabaseHelperImpl(private val appDatabase: AppDatabase):DatabaseHelper {
    override fun getUser(): Flow<List<User>> {
        return flow { emit(appDatabase.userDao().getAll()) }
    }

    override fun insertAll(user: List<User>): Flow<Unit> {
       return flow {
           appDatabase.userDao().insertAll(user)
           emit(Unit)
       }
    }
}