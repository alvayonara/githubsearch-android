package com.alvayonara.githubsearch.di

import com.alvayonara.githubsearch.core.domain.usecase.profile.ProfileInteractor
import com.alvayonara.githubsearch.core.domain.usecase.profile.ProfileUseCase
import com.alvayonara.githubsearch.core.domain.usecase.search.SearchInteractor
import com.alvayonara.githubsearch.core.domain.usecase.search.SearchUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideSearchUseCase(searchInteractor: SearchInteractor): SearchUseCase

    @Binds
    abstract fun provideProfileUseCase(profileInteractor: ProfileInteractor): ProfileUseCase
}