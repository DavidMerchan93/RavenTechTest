package com.davidmerchan.home.di

import com.davidmerchan.core.AppLogger
import com.davidmerchan.database.dao.ArticleDao
import com.davidmerchan.di.IODispatcher
import com.davidmerchan.home.data.repository.ArticlesLocalDatasource
import com.davidmerchan.home.data.repository.ArticlesRemoteDatasource
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import com.davidmerchan.home.domain.repository.ArticlesRemoteRepository
import com.davidmerchan.network.api.ArticlesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideArticlesRemoteRepository(
        appLogger: AppLogger,
        api: ArticlesApi,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): ArticlesRemoteRepository = ArticlesRemoteDatasource(appLogger, api, ioDispatcher)

    @Provides
    @Singleton
    fun provideArticlesLocalRepository(
        appLogger: AppLogger,
        articleDao: ArticleDao,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): ArticlesLocalRepository = ArticlesLocalDatasource(appLogger, articleDao, ioDispatcher)

}
