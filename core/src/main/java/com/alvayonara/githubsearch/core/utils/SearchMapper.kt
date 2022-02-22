package com.alvayonara.githubsearch.core.utils

import com.alvayonara.githubsearch.core.data.source.remote.response.search.SearchResponse
import com.alvayonara.githubsearch.core.domain.model.search.Search
import com.alvayonara.githubsearch.core.domain.model.search.SearchItem

object SearchMapper {
    fun SearchResponse.mapSearchResponsesToEntities() =
        Search(
            items = items?.map { searchItemResponse ->
                SearchItem(login = searchItemResponse.login ?: "")
            }.orEmpty()
        )
}