package com.alvayonara.githubsearch.di.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvayonara.githubsearch.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchViewModelModule {

    @SearchScope
    @Binds
    abstract fun bindSearchViewModelFactory(searchViewModelFactory: SearchViewModelFactory): ViewModelProvider.Factory

    @SearchScope
    @Binds
    @IntoMap
    @SearchViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel
}