package com.elotec.users.data.repository

import com.elotec.users.data.api.UsersApi
import com.elotec.users.data.local.UserDao
import com.elotec.users.data.mapper.UserDataMapper.toDomain
import com.elotec.users.data.mapper.UserEntityMapper.toDomain
import com.elotec.users.data.mapper.UserEntityMapper.toEntity
import com.elotec.users.domain.model.User
import com.elotec.users.domain.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi,
    private val userDao: UserDao
) : UsersRepository {
    override suspend fun getUserList(currentPage: Int): List<User> =
        api.getUserList(currentPage).map { user ->
            user.toDomain()
        }
    override suspend fun getUsersFromCache(): List<User> =
        userDao.getAllUsers().map { user ->
            user.toDomain()
        }

    override suspend fun saveUsersToCache(users: List<User>) {
        userDao.insertUsers(users = users.map { user -> user.toEntity() })
    }
}