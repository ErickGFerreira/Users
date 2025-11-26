package com.elotec.users.data.response

import com.elotec.users.domain.model.Company
import com.google.gson.annotations.SerializedName

data class CompanyResponse(
    @SerializedName("name") val name: String,
    @SerializedName("catchPhrase") val catchPhrase: String,
    @SerializedName("bs") val bs: String,
) {
    companion object {
        fun mockCompany() = CompanyResponse(
            name = "Temple Co.",
            catchPhrase = "Technology and Solutions",
            bs = "change customers life"
        )
    }
}