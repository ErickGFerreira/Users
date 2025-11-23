package com.elotec.users.inject

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonsModule {
    @Provides
    @Singleton
    fun providesGson() = Gson()
}