package com.elotec.users.inject

import com.elotec.users.domain.repository.UsersRepository
import com.elotec.users.domain.usecase.GetLocalUsersListUseCase
import com.elotec.users.domain.usecase.GetRemotePaginatedUsersUseCase
import com.elotec.users.domain.usecase.GetRemoteUserListUseCase
import com.elotec.users.domain.usecase.SaveUserListUseCase
import com.elotec.users.domain.usecase.UsersListUseCase
import com.elotec.users.utils.safe.safeRunDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetRemoteUserListUseCase(repository: UsersRepository) =
        GetRemoteUserListUseCase { currentPage ->
            safeRunDispatcher {
                repository.getUserList(currentPage = currentPage)
            }
        }

    @Provides
    fun provideGetRemotePaginatedUsersUseCase(repository: UsersRepository) =
        GetRemotePaginatedUsersUseCase { page ->
            safeRunDispatcher {
                repository.getUserListPaginated(page = page)
            }
        }

    @Provides
    fun provideGetLocalUsersListUseCase(repository: UsersRepository) =
        GetLocalUsersListUseCase {
            safeRunDispatcher {
                repository.getUsersFromCache()
            }
        }

    @Provides
    fun provideSaveUserListUseCase(repository: UsersRepository) =
        SaveUserListUseCase { users ->
            safeRunDispatcher {
                repository.saveUsersToCache(users)
            }
        }

    @Provides
    fun provideUserListUseCase(
        getLocalUsersListUseCase: GetLocalUsersListUseCase,
        getRemoteUserListUseCase: GetRemoteUserListUseCase,
        saveUserListUseCase: SaveUserListUseCase
    ) = UsersListUseCase(
        getLocalUsersListUseCase = getLocalUsersListUseCase,
        getRemoteUserListUseCase = getRemoteUserListUseCase,
        saveUserListUseCase = saveUserListUseCase
    )
}