package com.elotec.users.domain.model

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String,
) {
    companion object {
        fun mockCompany() = Company(
            name = "Temple Co.",
            catchPhrase = "Technology and Solutions",
            bs = "change customers life"
        )
    }
}