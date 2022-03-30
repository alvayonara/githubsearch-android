package com.alvayonara.githubsearch.core.data.source.remote.response

import com.alvayonara.githubsearch.core.data.source.remote.network.GithubService
import com.alvayonara.githubsearch.core.data.source.remote.response.search.SearchResponse
import com.alvayonara.githubsearch.core.domain.remote.ISearchRemoteSource
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRemoteSource @Inject constructor(private val githubService: GithubService) :
    ISearchRemoteSource {
    override fun searchUser(query: String, page: Int): Flowable<SearchResponse> =
        githubService.search(query, page)
}