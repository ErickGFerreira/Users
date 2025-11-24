package com.elotec.users.inject

import com.elotec.users.domain.repository.UsersRepository
import com.elotec.users.domain.usecase.GetLocalUsersListUseCase
import com.elotec.users.domain.usecase.GetRemoteUserListUseCase
import com.elotec.users.domain.usecase.SaveUserListUseCase
import com.elotec.users.domain.usecase.UserListUseCase
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
        GetRemoteUserListUseCase {
            safeRunDispatcher {
                repository.getUserList()
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
    ) = UserListUseCase(
        getLocalUsersListUseCase = getLocalUsersListUseCase,
        getRemoteUserListUseCase = getRemoteUserListUseCase,
        saveUserListUseCase = saveUserListUseCase
    )
}