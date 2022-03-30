package com.alvayonara.githubsearch.core.data.source.remote

import com.alvayonara.githubsearch.core.data.source.remote.response.ProfileRemoteSource
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.domain.repository.IProfileRepository
import com.alvayonara.githubsearch.core.utils.ProfileMapper.mapProfileResponsesToEntities
import com.alvayonara.githubsearch.core.utils.RepositoryMapper.mapRepositoryResponsesToEntities
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val profileRemoteSource: ProfileRemoteSource) :
    IProfileRepository {
    override fun getProfile(username: String): Flowable<Profile> =
        profileRemoteSource.getProfile(username).map {
            it.mapProfileResponsesToEntities()
        }

    override fun getRepository(username: String, page: Int): Flowable<List<Repository>> =
        profileRemoteSource.getRepository(username, page).map {
            it.mapRepositoryResponsesToEntities()
        }
}