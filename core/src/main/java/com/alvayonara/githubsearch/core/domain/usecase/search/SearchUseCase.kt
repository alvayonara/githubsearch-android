package com.alvayonara.githubsearch.core.domain.usecase.search

import com.alvayonara.githubsearch.core.domain.model.search.SearchItem
import com.alvayonara.githubsearch.core.utils.Constant
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

interface SearchUseCase {
    fun searchUser(
        query: String,
        page: Int = Constant.Services.FIRST_PAGE
    ): Flowable<List<SearchItem>>
}