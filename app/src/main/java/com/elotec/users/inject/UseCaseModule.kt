package com.elotec.users.inject

import com.elotec.users.domain.repository.UsersRepository
import com.elotec.users.domain.usecase.GetRemoteUserListUseCase
import com.elotec.users.utils.safe.safeRunDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetPokemonListUseCase(repository: UsersRepository) =
        GetRemoteUserListUseCase {
            safeRunDispatcher {
                repository.getUserList()
            }
        }
}