package com.elotec.users.domain.repository

import com.elotec.users.domain.model.User

interface UsersRepository {
    suspend fun getUserList(): List<User>
    suspend fun getUsersFromCache(): List<User>
    suspend fun saveUsersToCache(users: List<User>)
}
