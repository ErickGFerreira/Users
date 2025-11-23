package com.elotec.users.domain.usecase

import com.elotec.users.domain.model.User
import com.elotec.users.utils.safe.Result

fun interface GetRemoteUserListUseCase {
    suspend fun execute(): Result<List<User>>
}