package com.alvayonara.githubsearch.core.di

import android.content.Context
import com.alvayonara.githubsearch.core.domain.repository.IProfileRepository
import com.alvayonara.githubsearch.core.domain.repository.ISearchRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideSearchRepository(): ISearchRepository
    fun provideProfileRepository(): IProfileRepository
}