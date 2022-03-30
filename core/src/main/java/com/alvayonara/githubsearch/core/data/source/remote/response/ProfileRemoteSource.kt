package com.alvayonara.githubsearch.core.data.source.remote.response

import com.alvayonara.githubsearch.core.data.source.remote.network.GithubService
import com.alvayonara.githubsearch.core.data.source.remote.response.profile.ProfileResponse
import com.alvayonara.githubsearch.core.data.source.remote.response.profile.RepositoryResponse
import com.alvayonara.githubsearch.core.domain.remote.IProfileRemoteSource
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRemoteSource @Inject constructor(private val githubService: GithubService) :
    IProfileRemoteSource {
    override fun getProfile(username: String): Flowable<ProfileResponse> =
        githubService.profile(username)

    override fun getRepository(username: String, page: Int): Flowable<List<RepositoryResponse>> =
        githubService.repository(username, page)
}