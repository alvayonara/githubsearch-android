package com.alvayonara.githubsearch.di.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvayonara.githubsearch.ui.detail.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module
abstract class DetailViewModelModule {

    @DetailScope
    @Binds
    abstract fun bindDetailViewModelFactory(detailViewModelFactory: DetailViewModelFactory): ViewModelProvider.Factory

    @DetailScope
    @Binds
    @IntoMap
    @DetailViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel
}