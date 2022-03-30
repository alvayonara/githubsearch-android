package com.alvayonara.githubsearch.di.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvayonara.githubsearch.ui.detail.ProfileViewModel
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
    @DetailViewModelKey(ProfileViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: ProfileViewModel): ViewModel
}