package com.elotec.users.domain.usecase

import com.elotec.users.domain.model.User
import com.elotec.users.utils.safe.Result

fun interface GetRemotePaginatedUsersUseCase {
    suspend fun execute(page: Int): Result<List<User>>
}
