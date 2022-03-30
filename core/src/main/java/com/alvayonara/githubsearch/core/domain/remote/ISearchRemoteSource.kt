package com.alvayonara.githubsearch.core.domain.remote

import com.alvayonara.githubsearch.core.data.source.remote.response.search.SearchResponse
import io.reactivex.rxjava3.core.Flowable

interface ISearchRemoteSource {
    fun searchUser(query: String, page: Int): Flowable<SearchResponse>
}