package com.example.jetpack.ktpaging

import androidx.paging.PagingSource

class GitHubItemPagingSource(private val api:GitHubService) : PagingSource<Int, GithubAccountModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubAccountModel> {
        return try {
            val key = params.key ?:0
            val item = api.getAccount(key,params.loadSize)
            LoadResult.Page(
                    /**
                     * 请求接口返回的数据
                     */
                    data = item,
                    /**
                     * 分页的第一页其实位置
                     * 如果是第一页需要返回 null，否则会出现多次请求
                     */
                    prevKey = null,
                    /**
                     * 下一页的起始位置
                     * 设置为空，则没有加载更多的额效果，如果是最后一页则返回null
                     */
                    nextKey = item.lastOrNull()?.id
            )
        }catch (e:Exception){
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}