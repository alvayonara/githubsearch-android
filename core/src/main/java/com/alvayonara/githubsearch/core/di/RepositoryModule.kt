package com.alvayonara.githubsearch.core.di

import com.alvayonara.githubsearch.core.data.source.remote.ProfileRepository
import com.alvayonara.githubsearch.core.data.source.remote.SearchRepository
import com.alvayonara.githubsearch.core.domain.repository.IProfileRepository
import com.alvayonara.githubsearch.core.domain.repository.ISearchRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideSearchRepository(searchRepository: SearchRepository): ISearchRepository

    @Binds
    abstract fun provideProfileRepository(profileRepository: ProfileRepository): IProfileRepository
}