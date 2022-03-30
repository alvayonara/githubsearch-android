package com.alvayonara.githubsearch.di

import android.content.Context
import com.alvayonara.githubsearch.core.di.CoreComponent
import com.alvayonara.githubsearch.di.detail.DetailComponent
import com.alvayonara.githubsearch.di.search.SearchComponent
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi

@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, SubComponentModule::class]
)
@AppScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context, coreComponent: CoreComponent): AppComponent
    }

    fun searchComponent(): SearchComponent.Factory
    fun detailComponent(): DetailComponent.Factory
}