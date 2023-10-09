package com.example.core.di

import android.util.Log
import com.example.core.data.remote.network.ApiService
import com.example.core.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        val hostname = BuildConfig.BASE_URL.removePrefix("https://").removeSuffix("/anime/gogoanime/")
        Log.d("host", hostname)
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/yp72ygRrAJyxaoz7NVmdW28PxX/d/tXXmcoh9twefQk=")
            .add(hostname, "sha256/81Wf12bcLlFHQAfJluxnzZ6Frg+oJ9PWY/Wrwur8viQ=")
            .add(hostname, "sha256/hxqRlPTu1bMS/0DITB1SSu0vd4u/8l8TjPgfaAp63Gc=")
            .build()
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
}