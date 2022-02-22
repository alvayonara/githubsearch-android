package com.alvayonara.githubsearch.core.data.source.remote.response.search

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val items: List<SearchItemResponse>?
)

@JsonClass(generateAdapter = true)
data class SearchItemResponse(
    val login: String?
)