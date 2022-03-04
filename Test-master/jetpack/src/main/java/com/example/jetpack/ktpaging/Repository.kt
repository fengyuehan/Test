package com.example.jetpack.ktpaging

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun postOfData(id:Int):Flow<PagingData<GitHubAccount>>
}