package com.elotec.users.domain.model

import com.elotec.users.utils.EMPTY_STRING
import java.io.Serializable

data class Company(
    val name: String = EMPTY_STRING,
    val catchPhrase: String = EMPTY_STRING,
    val bs: String = EMPTY_STRING,
) : Serializable {
    companion object {
        fun mockCompany() = Company(
            name = "Temple Co.",
            catchPhrase = "Technology and Solutions",
            bs = "change customers life"
        )
    }
}