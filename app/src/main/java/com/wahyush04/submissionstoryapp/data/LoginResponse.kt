package com.wahyush04.submissionstoryapp.data

data class LoginResponse(
    var error : Boolean? = null,
    var message : String? = null,
    val loginResult : LoginResult? = null
)

data class LoginResult(
    val userId : String,
    val name : String,
    val token : String
)




