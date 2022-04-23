package com.test.di

import com.test.data.repository.ChallengeRepositoryImpl
import com.test.domain.repository.IChallengeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChallengeRepository(repository: ChallengeRepositoryImpl): IChallengeRepository

}