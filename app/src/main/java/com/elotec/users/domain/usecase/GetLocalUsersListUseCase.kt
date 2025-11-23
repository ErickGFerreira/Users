package com.elotec.users.domain.usecase

import com.elotec.users.domain.model.User

fun interface GetLocalUsersListUseCase {
    suspend fun execute(): Result<List<User>>
}