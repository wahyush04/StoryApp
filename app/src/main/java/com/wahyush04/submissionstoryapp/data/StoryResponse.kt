package com.wahyush04.submissionstoryapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class StoryResponse (
    val error : Boolean,
    val message : String,
    val listStory : ArrayList<ListStory>
)

@Entity(tableName = "story")
data class ListStory(
    @PrimaryKey
    @field:SerializedName("id")
    val id : String,
    @field:SerializedName("name")
    val name : String,
    @field:SerializedName("description")
    val description : String,
    @field:SerializedName("photoUrl")
    val photoUrl : String,
    @field:SerializedName("createdAt")
    val createdAt : String,
    @field:SerializedName("lat")
    val lat : Float,
    @field:SerializedName("lon")
    val lon : Float
)