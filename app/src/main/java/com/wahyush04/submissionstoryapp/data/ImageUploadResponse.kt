package com.wahyush04.submissionstoryapp.data

import com.google.gson.annotations.SerializedName

class ImageUploadResponse (
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String
)