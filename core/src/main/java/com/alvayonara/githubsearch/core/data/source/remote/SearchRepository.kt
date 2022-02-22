package com.alvayonara.githubsearch.core.data.source.remote

import com.alvayonara.githubsearch.core.data.source.remote.response.SearchRemoteSource
import com.alvayonara.githubsearch.core.domain.model.search.SearchItem
import com.alvayonara.githubsearch.core.domain.repository.ISearchRepository
import com.alvayonara.githubsearch.core.utils.SearchMapper.mapSearchResponsesToEntities
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val searchRemoteSource: SearchRemoteSource
) : ISearchRepository {

    override suspend fun searchUser(query: String, page: Int): List<SearchItem> =
        searchRemoteSource.searchUser(query, page).mapSearchResponsesToEntities().items
}