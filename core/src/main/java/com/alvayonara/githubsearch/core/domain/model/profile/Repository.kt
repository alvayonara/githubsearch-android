package com.alvayonara.githubsearch.core.domain.model.profile

data class Repository(
    val name: String,
    val owner: Owner? = null,
    val description: String,
    val stargazersCount: Int,
    val updatedAt: String
)

data class Owner(
    val avatarUrl: String
)