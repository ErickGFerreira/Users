package com.elotec.users.data.api

import com.elotec.users.data.response.UserResponse
import retrofit2.http.GET

interface UsersApi {
    @GET("users")
    suspend fun getUserList(): List<UserResponse>
}