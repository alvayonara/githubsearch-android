package com.alvayonara.githubsearch

import android.app.Application
import com.alvayonara.githubsearch.core.di.CoreComponent
import com.alvayonara.githubsearch.core.di.DaggerCoreComponent
import com.alvayonara.githubsearch.di.AppComponent
import com.alvayonara.githubsearch.di.DaggerAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@FlowPreview
class GitHubApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext, coreComponent)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}