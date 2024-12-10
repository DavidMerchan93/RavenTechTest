package com.davidmerchan.network.di

import com.davidmerchan.network.api.ArticlesApi
import com.davidmerchan.network.manager.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApiManager(): Retrofit = RetrofitBuilder.create()

    @Provides
    @Singleton
    fun provideArticlesApi(
        retrofit: Retrofit
    ): ArticlesApi = retrofit.create(ArticlesApi::class.java)
}
