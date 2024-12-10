package com.davidmerchan.home.di

import com.davidmerchan.home.data.repository.ArticlesLocalDatasource
import com.davidmerchan.home.data.repository.ArticlesRemoteDatasource
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import com.davidmerchan.home.domain.repository.ArticlesRemoteRepository
import com.davidmerchan.network.api.ArticlesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideArticlesRemoteRepository(
        api: ArticlesApi
    ): ArticlesRemoteRepository = ArticlesRemoteDatasource(api)

    @Provides
    @Singleton
    fun provideArticlesLocalRepository(): ArticlesLocalRepository = ArticlesLocalDatasource()

}
