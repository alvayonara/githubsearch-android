package com.alvayonara.githubsearch.di

import com.alvayonara.githubsearch.di.detail.DetailComponent
import com.alvayonara.githubsearch.di.search.SearchComponent
import dagger.Module

@Module(
    subcomponents = [
        SearchComponent::class,
        DetailComponent::class
    ]
)
class SubComponentModule