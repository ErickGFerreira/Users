package com.elotec.users.domain.usecase

import com.elotec.users.domain.model.User
import com.elotec.users.utils.error.Error
import com.elotec.users.utils.safe.Result
import com.elotec.users.utils.safe.getOrNull
import com.elotec.users.utils.safe.isFailure
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class PushProvisioningSamsungUseCaseTest {
    private val saveUserListUseCase: SaveUserListUseCase = mockk()

    private val getRemoteUserListUseCase: GetRemoteUserListUseCase = mockk()

    private val getLocalUsersListUseCase: GetLocalUsersListUseCase = mockk()

    private lateinit var useCase: UsersListUseCase

    @Before
    fun setup() {
        useCase =
            UsersListUseCase(
                getLocalUsersListUseCase = getLocalUsersListUseCase,
                getRemoteUserListUseCase = getRemoteUserListUseCase,
                saveUserListUseCase = saveUserListUseCase,
            )
    }

    @Test
    fun `given get push provisioning data success, when execute, then return success`() =
        runBlocking {
            // given
            mockGetRemoteUserListWithSuccess()
            mockSaveUserListWithSuccess()

            // when
            val result = useCase.execute(currentPage = Random.nextInt())

            // then
            assertThat(result.getOrNull()).isEqualTo(USER_MOCK)
            coVerifyOrder {
                getRemoteUserListUseCase.execute(currentPage = any())
                saveUserListUseCase.execute(USER_MOCK)
            }
        }

    @Test
    fun `given get push provisioning data fails, when execute, then return failure`() =
        runBlocking {
            // given
            mockGetRemoteUserListWithFailure()
            mockGetLocalUserListWithSuccess()

            // when
            val result = useCase.execute(currentPage = Random.nextInt())

            // then
            assertThat(result.getOrNull()).isEqualTo(USER_MOCK)
            coVerifyOrder {
                getRemoteUserListUseCase.execute(currentPage = any())
                getLocalUsersListUseCase.execute()
            }
        }

    @Test
    fun `given remote fails and local fails, when execute, then throw exception`() =
        runBlocking {
            // given
            val localError = Error.mock()
            mockGetRemoteUserListWithFailure()
            mockGetLocalUserListWithFailure(localError)

            // when
            val result = useCase.execute(currentPage = Random.nextInt())

            // then
            assertThat(result.isFailure()).isTrue()
            assertThat((result as Result.Failure).error).isEqualTo(localError)
            coVerifyOrder {
                getRemoteUserListUseCase.execute(currentPage = any())
                getLocalUsersListUseCase.execute()
            }
        }

    @Test
    fun `given remote returns empty list, when execute, then save and return empty list`() =
        runBlocking {
            // given
            val emptyList = emptyList<User>()
            mockGetRemoteUserListWithSuccess(emptyList)
            mockSaveUserListWithSuccess()

            // when
            val result = useCase.execute(currentPage = Random.nextInt())

            // then
            assertThat(result.getOrNull()).isEmpty()
            coVerifyOrder {
                getRemoteUserListUseCase.execute(currentPage = any())
                saveUserListUseCase.execute(emptyList)
            }
        }

    // Helpers

    private fun mockGetRemoteUserListWithSuccess(data: List<User> = USER_MOCK) {
        coEvery { getRemoteUserListUseCase.execute(currentPage = any()) } returns Result.Success(
            data = data
        )
    }

    private fun mockGetLocalUserListWithSuccess() {
        val result = Result.Success(data = USER_MOCK)
        coEvery {
            getLocalUsersListUseCase.execute()
        } returns result
    }

    private fun mockGetRemoteUserListWithFailure() {
        coEvery {
            getRemoteUserListUseCase.execute(currentPage = any())
        } returns Result.Failure(error = Error.mock())
    }

    private fun mockGetLocalUserListWithFailure(error: Error) {
        coEvery {
            getLocalUsersListUseCase.execute()
        } returns Result.Failure(error = error)
    }

    private fun mockSaveUserListWithSuccess() {
        coEvery {
            saveUserListUseCase.execute(list = any())
        } just runs
    }

    private companion object {
        val USER_MOCK = User.mockList()
    }
}
