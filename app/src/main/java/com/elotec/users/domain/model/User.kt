package com.elotec.users.domain.model

import com.elotec.users.utils.EMPTY_STRING
import java.io.Serializable

data class User(
    val id: Int = 0,
    val name: String = EMPTY_STRING,
    val username: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val address: UserAddress = UserAddress(),
    val phone: String = EMPTY_STRING,
    val website: String = EMPTY_STRING,
    val company: Company = Company(),
) : Serializable {
    companion object {
        fun mockUser() = User(
            id = 1,
            name = "Carlos",
            username = "Carlitos",
            email = "carlosnogueira@gmail.com",
            address = UserAddress.mockUserAddress(),
            phone = "27999999999",
            website = "https://www.carlitos.com",
            company = Company.mockCompany()
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