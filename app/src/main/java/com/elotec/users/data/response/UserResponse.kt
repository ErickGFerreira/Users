package com.elotec.users.data.response

import com.elotec.users.data.respone.UserAddressResponse
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("address") val address: UserAddressResponse,
    @SerializedName("phone") val phone: String,
    @SerializedName("website") val website: String,
    @SerializedName("company") val company: CompanyResponse,
)