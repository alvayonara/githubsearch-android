package com.alvayonara.githubsearch.core.utils

import com.alvayonara.githubsearch.core.data.source.remote.response.profile.RepositoryResponse
import com.alvayonara.githubsearch.core.domain.model.profile.Owner
import com.alvayonara.githubsearch.core.domain.model.profile.Repository

object RepositoryMapper {
    fun List<RepositoryResponse>.mapRepositoryResponsesToEntities() =
        map {
            Repository(
                name = it.name ?: "-",
                owner = it.owner?.let { owner ->
                    Owner(
                        avatarUrl = owner.avatarUrl ?: "-"
                    )
                },
                description = it.description ?: "-",
                stargazersCount = it.stargazersCount ?: 0,
                updatedAt = it.updatedAt ?: "-"
            )
        }
}