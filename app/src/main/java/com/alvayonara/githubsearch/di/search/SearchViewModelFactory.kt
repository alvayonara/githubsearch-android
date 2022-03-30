package com.alvayonara.githubsearch.di.search

import androidx.lifecycle.ViewModel
import com.alvayonara.githubsearch.di.ViewModelFactory
import javax.inject.Inject
import javax.inject.Provider

@SearchScope
class SearchViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelFactory(creators)