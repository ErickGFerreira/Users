package com.elotec.users.domain.model

data class LabelValueData(
    val label: String,
    val value: String,
) {
    companion object {
        fun mockLabelValueDataList() = listOf(
            LabelValueData(
                label = "Nome",
                value = "Carlos"
            ),
            LabelValueData(
                label = "Celular",
                value = "2799999999"
            ),
            LabelValueData(
                label = "Email",
                value = "carlos123@gmail.com"
            ),
        )
    }
}