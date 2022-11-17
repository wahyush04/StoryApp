package com.wahyush04.submissionstoryapp.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wahyush04.submissionstoryapp.api.ApiConfig
import com.wahyush04.submissionstoryapp.data.*
import com.wahyush04.submissionstoryapp.di.Injection
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call

class MainViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    val uploadImageResponse = MutableLiveData<ImageUploadResponse>()
    val storyResponse = MutableLiveData<StoryResponse>()

    fun getStory(token: String):LiveData<PagingData<ListStory>>{
        return storyRepository.getStory(token).cachedIn(viewModelScope)
    }

    fun getStoryLocation(token: String){
        viewModelScope.launch {
            storyResponse.postValue(storyRepository.getStoryLocation(token))
        }
    }

    fun login(email: String, password:String): Call<LoginResponse> {
        val request = LoginRequest()
        request.email = email
        request.password = password
        return ApiConfig.getApiService().userLogin(request)
    }

    fun register(name: String,email: String, password:String): Call<RegisterResponse> {
        val request = RegisterRequest()
        request.name = name
        request.email = email
        request.password = password
        return ApiConfig.getApiService().userRegister(request)
    }

    fun uploadStory(token:String, file: MultipartBody.Part, description: String, lat:Float, lon:Float){
        viewModelScope.launch {
            uploadImageResponse.postValue(storyRepository.uploadStory(token, file, description, lat, lon))
        }
    }
}


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}