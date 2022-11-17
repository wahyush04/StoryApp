package com.wahyush04.submissionstoryapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wahyush04.submissionstoryapp.api.ApiService


class StoryPagingSource(private val apiService: ApiService, val token: String) : PagingSource<Int, ListStory>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStory> {
        return try {
            val page = params.key ?: 1
            val responseData : StoryResponse = apiService.getListStory("Bearer $token", page, params.loadSize, 1)

            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}