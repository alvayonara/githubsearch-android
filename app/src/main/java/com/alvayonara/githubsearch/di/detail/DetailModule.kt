package com.alvayonara.githubsearch.di.detail

import android.content.Context
import com.alvayonara.githubsearch.core.ui.profile.ProfileController
import dagger.Module
import dagger.Provides

@Module
class DetailModule {

    @Provides
    @DetailScope
    fun provideProfileController(context: Context) = ProfileController(context)
}