package com.elotec.users.data.api

import com.elotec.users.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET("users")
    suspend fun getUserList(@Query("currentPage") currentPage: Int): List<UserResponse>
}