package com.granson.protopoint.di

import com.granson.protopoint.BuildConfig
import com.granson.protopoint.utils.Constants
import com.granson.protopoint.repository.ProtoRepository
import com.granson.protopoint.interfaces.ProtoService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Available only for MainActivity
object AppModule {

    @Singleton
    @Provides // Provides API functions to injectables
    fun provideProtoRepository(
        protoService: ProtoService
    ) = ProtoRepository(protoService)


    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(): ProtoService {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor { interceptor ->
                val request = interceptor.request()
                val requestOptions = request.newBuilder()
                    .header("Content-Type", "application/json")
                    .build()

                return@addInterceptor interceptor.proceed(requestOptions)
            }
            .build()

        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(ProtoService::class.java)
    }

}