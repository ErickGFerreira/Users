package com.elotec.users.domain.usecase

import com.elotec.users.domain.model.User
import com.elotec.users.utils.safe.Result

fun interface SaveUserListUseCase {
    suspend fun execute(list: List<User>)
}