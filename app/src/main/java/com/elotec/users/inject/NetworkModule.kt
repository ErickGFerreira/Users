package com.elotec.users.inject

import android.content.Context
import androidx.room.Room
import com.elotec.users.common.security.SecurityEndpoints
import com.elotec.users.common.security.SecurityEndpoints.Database.DATABASE_NAME
import com.elotec.users.data.api.UsersApi
import com.elotec.users.data.local.UserDao
import com.elotec.users.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
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
    fun providesApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = DATABASE_NAME

        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}