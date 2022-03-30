package com.alvayonara.githubsearch.core.domain.repository

import com.alvayonara.githubsearch.core.domain.model.search.SearchItem
import io.reactivex.rxjava3.core.Flowable

interface ISearchRepository {
    fun searchUser(query: String, page: Int): Flowable<List<SearchItem>>
}