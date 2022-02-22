package com.alvayonara.githubsearch.core.domain.repository

import com.alvayonara.githubsearch.core.domain.model.search.SearchItem

interface ISearchRepository {
    suspend fun searchUser(query: String, page: Int): List<SearchItem>
}