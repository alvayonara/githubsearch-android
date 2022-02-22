package com.alvayonara.githubsearch.core.domain.remote

import com.alvayonara.githubsearch.core.data.source.remote.response.search.SearchResponse

interface ISearchRemoteSource {
    suspend fun searchUser(query: String, page: Int): SearchResponse
}