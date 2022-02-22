package com.alvayonara.githubsearch.core.data.source.remote

import com.alvayonara.githubsearch.core.data.source.remote.response.ProfileRemoteSource
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.domain.repository.IProfileRepository
import com.alvayonara.githubsearch.core.utils.ProfileMapper.mapProfileResponsesToEntities
import com.alvayonara.githubsearch.core.utils.RepositoryMapper.mapRepositoryResponsesToEntities
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val profileRemoteSource: ProfileRemoteSource) :
    IProfileRepository {
    override suspend fun getProfile(username: String): Profile =
        profileRemoteSource.getProfile(username).mapProfileResponsesToEntities()

    override suspend fun getRepository(username: String, page: Int): List<Repository> =
        profileRemoteSource.getRepository(username, page).mapRepositoryResponsesToEntities()
}