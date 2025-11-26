package com.elotec.users.data.response

import com.elotec.users.data.respone.UserAddressResponse
import com.elotec.users.domain.model.Company
import com.elotec.users.domain.model.User
import com.elotec.users.domain.model.UserAddress
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
) {
    companion object {
        fun mockUser() = UserResponse(
            id = 1,
            name = "Carlos",
            username = "Carlitos",
            email = "carlosnogueira@gmail.com",
            address = UserAddressResponse.mockUserAddress(),
            phone = "27999999999",
            website = "https://www.carlitos.com",
            company = CompanyResponse.mockCompany()
        )

        fun mockList() = listOf(
            mockUser(),
            mockUser().copy(id = 2, name = "Erick", email = "erick@gmail.com"),
            mockUser().copy(id = 3, name = "Glauber", email = "glauber@gmail.com"),
            mockUser().copy(id = 4, name = "Samara", email = "samara@gmail.com"),
            mockUser().copy(id = 5, name = "Lorena", email = "lorena@gmail.com"),
            mockUser().copy(id = 6, name = "Mila", email = "mila@gmail.com"),
        )
    }
}