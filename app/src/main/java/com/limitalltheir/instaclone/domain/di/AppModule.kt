package com.limitalltheir.instaclone.domain.di

import android.content.Context
import com.limitalltheir.instaclone.util.ResourcesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideResourcesHelper(@ApplicationContext context: Context) = ResourcesHelper(context)
}