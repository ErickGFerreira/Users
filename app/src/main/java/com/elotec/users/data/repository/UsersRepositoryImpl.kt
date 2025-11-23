package com.elotec.users.data.repository

import com.elotec.users.data.api.UsersApi
import com.elotec.users.data.mapper.UserDataMapper.toUser
import com.elotec.users.domain.model.User
import com.elotec.users.domain.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi,
) : UsersRepository {
    override suspend fun getUserList(): List<User> =
        api.getUserList().map {
            it.toUser()
        }
}