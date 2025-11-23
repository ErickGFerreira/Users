package com.elotec.users.domain.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: UserAddress,
    val phone: String,
    val website: String,
    val company: Company,
) {
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
    }
}