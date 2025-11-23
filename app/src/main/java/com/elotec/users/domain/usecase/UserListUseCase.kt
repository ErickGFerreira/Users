package com.elotec.users.domain.usecase

import com.elotec.users.utils.safe.getOrNull
import com.elotec.users.utils.safe.safeRunDispatcher
import javax.inject.Inject

class UserListUseCase @Inject constructor(
    private val getLocalUsersListUseCase: GetLocalUsersListUseCase,
    private val getRemoteUserListUseCase: GetRemoteUserListUseCase,
    private val saveUserListUseCase: SaveUserListUseCase
) {
    suspend fun execute() = safeRunDispatcher {
        getRemoteUserListUseCase.execute().getOrNull()
            ?.also { users -> saveUserListUseCase.execute(users) }
            ?: getLocalUsersListUseCase.execute().getOrThrow()
    }
}