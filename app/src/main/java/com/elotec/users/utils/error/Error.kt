package com.elotec.users.utils.error

data class Error(
    val code: String = "",
    val title: String = "",
    val message: String = "",
) {
    companion object {
        fun mock() = Error(
            code = "AUT-000",
            title = "Ops, ocorreu um erro!",
            message = "Falha de comunicação. Tente novamente mais tarde",
        )
    }
}
