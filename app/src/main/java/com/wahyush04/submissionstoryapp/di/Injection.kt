package com.wahyush04.submissionstoryapp.di

import android.content.Context
import com.wahyush04.submissionstoryapp.api.ApiConfig
import com.wahyush04.submissionstoryapp.data.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}