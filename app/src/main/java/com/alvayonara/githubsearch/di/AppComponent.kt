package com.alvayonara.githubsearch.di

import android.content.Context
import com.alvayonara.githubsearch.core.di.CoreComponent
import com.alvayonara.githubsearch.ui.detail.ProfileFragment
import com.alvayonara.githubsearch.ui.search.SearchFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi

@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class, EpoxyModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context, coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: SearchFragment)
    fun inject(fragment: ProfileFragment)
}