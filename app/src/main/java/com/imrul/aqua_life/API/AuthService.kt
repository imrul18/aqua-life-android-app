package com.imrul.aqua_life.API

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// Define a data class to hold login request payload
data class LoginRequest(
    val username: String,
    val password: String,
    val scope: String = "aqualifeapp"
)

// Define a data class to parse the login response
data class LoginResponse(
    val status: String,
    val data: Any
)

interface AuthService {
    @Headers(
        "Accept: application/json"
    )

    @POST("auth/token")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
