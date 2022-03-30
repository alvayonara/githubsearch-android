package com.alvayonara.githubsearch.di.detail

import androidx.lifecycle.ViewModel
import com.alvayonara.githubsearch.di.ViewModelFactory
import javax.inject.Inject
import javax.inject.Provider

@DetailScope
class DetailViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelFactory(creators)