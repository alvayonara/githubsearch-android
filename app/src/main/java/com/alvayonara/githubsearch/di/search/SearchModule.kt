package com.alvayonara.githubsearch.di.search

import android.content.Context
import com.alvayonara.githubsearch.core.ui.search.SearchController
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    @SearchScope
    fun provideSearchController(context: Context) = SearchController(context)
}