package com.davidmerchan.database.di

import android.content.Context
import androidx.room.Room
import com.davidmerchan.database.AppDatabase
import com.davidmerchan.database.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "RavenTechTestDatabase"
    ).build()

    @Provides
    @Singleton
    fun provideArticleDao(
        database: AppDatabase
    ): ArticleDao = database.articleDao()
}
