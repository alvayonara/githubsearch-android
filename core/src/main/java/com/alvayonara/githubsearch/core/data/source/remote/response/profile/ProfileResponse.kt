package com.alvayonara.githubsearch.core.data.source.remote.response.profile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileResponse(
    @Json(name = "avatar_url")
    val avatarUrl: String?,
    val name: String?,
    val login: String?,
    val bio: String?,
    val company: String?,
    val email: String?,
    val followers: String?,
    val following: String?
)