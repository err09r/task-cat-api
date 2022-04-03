package com.app.catapi.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.catapi.data.remote.api.CatApi
import com.app.catapi.data.remote.dto.CatDto
import retrofit2.HttpException

class CatPagingSource(private val api: CatApi) : PagingSource<Int, CatDto>() {

    override fun getRefreshKey(state: PagingState<Int, CatDto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatDto> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        val response = api.loadCatPage(page = page, limit = pageSize)
        return if (response.isSuccessful) {
            val result = response.body() ?: emptyList()

            val nextKey = if (result.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(data = result, prevKey = prevKey, nextKey = nextKey)
        } else {
            LoadResult.Error(HttpException(response))
        }
    }
}