package com.elotec.users.data.repository

import com.elotec.users.data.api.UsersApi
import com.elotec.users.data.local.UserDao
import com.elotec.users.data.local.entity.UserEntity
import com.elotec.users.data.response.UserResponse
import com.elotec.users.domain.model.User
import com.elotec.users.domain.repository.UsersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException
import kotlin.random.Random

class UsersRepositoryImplTest {
    private val api = mockk<UsersApi>(relaxed = true)
    private val userDao = mockk<UserDao>(relaxed = true)
    private lateinit var repository: UsersRepository

    @Before
    fun setUp() {
        repository = UsersRepositoryImpl(api = api, userDao = userDao)
    }

    @Test
    fun `given success, when get getUserList from local, then return USERS_ENTITY`() =
        runBlocking {
            // given
            mockUserListLocalSuccess()

            // when
            val result = repository.getUsersFromCache()

            // then
            coVerify {
                userDao.getAllUsers()
            }
            assertEquals(USERS_LIST, result)
        }

    @Test
    fun `given success, when get getUserList from remote, then return USER_LIST`() =
        runBlocking {
            // given
            mockUserListSuccess()
            val page = Random.nextInt()

            // when
            val result = repository.getUserList(currentPage = page)

            // then
            coVerify {
                api.getUserList(
                    currentPage = page,
                )
            }
            assertEquals(USERS_LIST, result)
        }

    @Test(expected = SocketTimeoutException::class)
    fun `given failure, when getUserList, then throws SocketTimeoutException`() =
        runBlocking<Unit> {
            // given
            mockUserListFailure()

            // when
            repository.getUserList(currentPage = Random.nextInt())
        }

    @Test(expected = Exception::class)
    fun `given failure, when getUsersFromCache, then throws Exception`() =
        runBlocking<Unit> {
            // given
            mockUserListLocalFailure()

            // when
            repository.getUsersFromCache()
        }

    @Test(expected = Exception::class)
    fun `given failure, when saveUsersToCache, then throws Exception`() =
        runBlocking<Unit> {
            // given
            mockSaveUserListFailure()

            // when
            repository.saveUsersToCache(users = USERS_LIST)
        }

    // Helpers

    private fun mockUserListLocalSuccess() {
        coEvery {
            userDao.getAllUsers()
        } returns USERS_ENTITY
    }


    private fun mockUserListSuccess() {
        coEvery {
            api.getUserList(
                currentPage = any(),
            )
        } returns USERS_RESPONSE
    }

    private fun mockUserListFailure() {
        coEvery {
            api.getUserList(
                currentPage = any(),
            )
        } throws SocketTimeoutException()
    }

    private fun mockUserListLocalFailure() {
        coEvery {
            userDao.getAllUsers()
        } throws Exception("Database error")
    }

    private fun mockSaveUserListFailure() {
        coEvery {
            userDao.insertUsers(users = any())
        } throws Exception("Database insert error")
    }

    private companion object {
        val USERS_LIST = User.mockList()
        val USERS_RESPONSE = UserResponse.mockList()

        val USERS_ENTITY = UserEntity.mockList()
    }
}
