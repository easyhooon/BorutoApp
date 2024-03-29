package com.kenshi.borutoapp.di

import androidx.paging.ExperimentalPagingApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kenshi.borutoapp.BuildConfig
import com.kenshi.borutoapp.data.local.BorutoDatabase
import com.kenshi.borutoapp.data.remote.BorutoApi
import com.kenshi.borutoapp.data.repository.RemoteDataSourceImpl
import com.kenshi.borutoapp.domain.repository.RemoteDataSource
import com.kenshi.borutoapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            .apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
    }

    @Provides
    @Singleton
    fun providerHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15,TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        //val contentType = MediaType.get("application/json")
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideBorutoApi(retrofit: Retrofit): BorutoApi {
        return retrofit.create(BorutoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        borutoApi: BorutoApi,
        borutoDatabase: BorutoDatabase
    ) : RemoteDataSource {
        return RemoteDataSourceImpl(
            borutoApi = borutoApi,
            borutoDatabase = borutoDatabase
        )
    }
}