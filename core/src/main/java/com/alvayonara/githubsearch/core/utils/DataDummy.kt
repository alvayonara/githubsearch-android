package com.alvayonara.githubsearch.core.utils

import com.alvayonara.githubsearch.core.data.source.remote.response.profile.OwnerResponse
import com.alvayonara.githubsearch.core.data.source.remote.response.profile.ProfileResponse
import com.alvayonara.githubsearch.core.data.source.remote.response.profile.RepositoryResponse
import com.alvayonara.githubsearch.core.data.source.remote.response.search.SearchItemResponse
import com.alvayonara.githubsearch.core.data.source.remote.response.search.SearchResponse
import com.alvayonara.githubsearch.core.utils.ProfileMapper.mapProfileResponsesToEntities
import com.alvayonara.githubsearch.core.utils.RepositoryMapper.mapRepositoryResponsesToEntities
import com.alvayonara.githubsearch.core.utils.SearchMapper.mapSearchResponsesToEntities

object DataDummy {

    fun getSearchResponses() = SearchResponse(
        items = listOf(
            SearchItemResponse(
                login = "alvayonara"
            )
        )
    )

    fun getSearchItem() = getSearchResponses().mapSearchResponsesToEntities().items

    fun getProfileResponses() = ProfileResponse(
        avatarUrl = "https://avatars.githubusercontent.com/u/42828307?v=4",
        name = "Alva Yonara Puramandya",
        login = "alvayonara",
        bio = "Software Engineer",
        company = "Jakarta, Indonesia",
        email = "alvayonara@gmail.com",
        followers = "1200",
        following = "120"
    )

    fun getProfile() = getProfileResponses().mapProfileResponsesToEntities()

    fun getRepositoryResponses() = listOf(
        RepositoryResponse(
            name = "clean-architecture",
            owner = OwnerResponse(
                    avatarUrl = "https://avatars.githubusercontent.com/u/42828307?v=4"
                ),
            description = "Simple Apps using Clean Architecture.",
            stargazersCount = 211,
            updatedAt = "2018-08-30T05:36:23Z"
        )
    )

    fun getRepository() = getRepositoryResponses().mapRepositoryResponsesToEntities()

    fun getDetailProfile() = listOf(
        (Constant.DetailProfile.PROFILE to getProfile()),
        (Constant.DetailProfile.REPOSITORY to getRepository())
    )
}