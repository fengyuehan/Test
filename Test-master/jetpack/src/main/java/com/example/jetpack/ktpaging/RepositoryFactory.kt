package com.example.jetpack.ktpaging

import androidx.paging.PagingConfig

class RepositoryFactory(val api:GitHubService) {
    fun makeGutHubRepository():Repository = GitHubRepositoryImpl(pageConfig,api,Model2GitHubMapper())

    val pageConfig = PagingConfig(
            /**
             * 每页显示的数据大小
             */
            pageSize = 20,
            /**
             * 开启占位符
             */
            enablePlaceholders = false,
            /**
             * 预刷新的距离，距离最后一个 item 多远时加载数据
             */
            prefetchDistance = 3,

            /**
             * internal const val DEFAULT_INITIAL_PAGE_MULTIPLIER = 3
             * val initialLoadSize: Int = pageSize * DEFAULT_INITIAL_PAGE_MULTIPLIER,
             */
            initialLoadSize = 60
    )
}