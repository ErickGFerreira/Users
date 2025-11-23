package com.elotec.users.inject

import com.elotec.users.data.api.UsersApi
import com.elotec.users.data.repository.UsersRepositoryImpl
import com.elotec.users.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideUsersRepository(api: UsersApi): UsersRepository =
        UsersRepositoryImpl(api = api)
}