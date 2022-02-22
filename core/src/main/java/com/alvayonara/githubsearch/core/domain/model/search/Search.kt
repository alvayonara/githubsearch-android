package com.alvayonara.githubsearch.core.domain.model.search

data class Search(
    val items: List<SearchItem>
)

data class SearchItem(
    val login: String
)
