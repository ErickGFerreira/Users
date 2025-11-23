package com.elotec.users.inject

import com.elotec.users.common.security.SecurityEndpoints
import com.elotec.users.data.api.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(httpLoggingInterceptor)



        return Retrofit.Builder().baseUrl(SecurityEndpoints.UsersBackend.API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    fun providesCountriesApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }
}