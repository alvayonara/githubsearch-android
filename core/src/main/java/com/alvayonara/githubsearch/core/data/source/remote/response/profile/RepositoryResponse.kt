package com.alvayonara.githubsearch.core.data.source.remote.response.profile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryResponse(
    val name: String?,
    val owner: OwnerResponse?,
    val description: String?,
    @Json(name = "stargazers_count")
    val stargazersCount: Int?,
    @Json(name = "updated_at")
    val updatedAt: String?,
)

@JsonClass(generateAdapter = true)
data class OwnerResponse(
    @Json(name = "avatar_url")
    val avatarUrl: String?
)