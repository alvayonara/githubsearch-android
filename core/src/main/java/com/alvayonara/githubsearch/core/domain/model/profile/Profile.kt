package com.alvayonara.githubsearch.core.domain.model.profile

data class Profile(
    val avatarUrl: String,
    val name: String,
    val login: String,
    val bio: String,
    val company: String,
    val email: String,
    val followers: String,
    val following: String
)
