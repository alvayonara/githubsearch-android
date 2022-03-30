package com.alvayonara.githubsearch.di.detail

import com.alvayonara.githubsearch.ui.detail.ProfileFragment
import dagger.Subcomponent

@DetailScope
@Subcomponent(modules = [DetailViewModelModule::class, DetailModule::class])
interface DetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailComponent
    }

    fun inject(profileFragment: ProfileFragment)
}