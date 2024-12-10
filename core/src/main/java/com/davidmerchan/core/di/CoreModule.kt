package com.davidmerchan.core.di

import com.davidmerchan.core.AppLogger
import com.davidmerchan.core.TimberLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideLogger(): AppLogger = TimberLogger()
}
