package com.wahyush04.submissionstoryapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.wahyush04.submissionstoryapp.api.ApiService
import okhttp3.MultipartBody

class StoryRepository(private val apiService: ApiService) {
    fun getStory(token: String): LiveData<PagingData<ListStory>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData
    }

    suspend fun getStoryLocation(token: String): StoryResponse{
        return apiService.getStory(token, 1)
    }

    suspend fun uploadStory(token:String, file: MultipartBody.Part, description: String, lat:Float, lon:Float): ImageUploadResponse{
        return apiService.uploadImage(token, file, description, lat, lon,)
    }
}