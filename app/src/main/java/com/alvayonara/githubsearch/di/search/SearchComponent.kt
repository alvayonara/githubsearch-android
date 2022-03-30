package com.alvayonara.githubsearch.di.search

import com.alvayonara.githubsearch.ui.search.SearchFragment
import dagger.Subcomponent

@SearchScope
@Subcomponent(modules = [SearchViewModelModule::class, SearchModule::class])
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchComponent
    }

    fun inject(searchFragment: SearchFragment)
}