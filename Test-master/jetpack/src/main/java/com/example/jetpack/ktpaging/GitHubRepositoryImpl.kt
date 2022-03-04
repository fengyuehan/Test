package com.example.jetpack.ktpaging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitHubRepositoryImpl(
        val pageConfig:PagingConfig,
        val api:GitHubService,
        val mapper:Mapper<GithubAccountModel,GitHubAccount>
) :Repository{
    override fun postOfData(id: Int): Flow<PagingData<GitHubAccount>> {
        return Pager(pageConfig){
            GitHubItemPagingSource(api)
        }.flow.map {
            pageData -> pageData.map { mapper.map(it) }
        }
    }
}