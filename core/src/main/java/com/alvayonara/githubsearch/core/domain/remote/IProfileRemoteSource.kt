package com.alvayonara.githubsearch.core.domain.remote

import com.alvayonara.githubsearch.core.data.source.remote.response.profile.ProfileResponse
import com.alvayonara.githubsearch.core.data.source.remote.response.profile.RepositoryResponse
import io.reactivex.rxjava3.core.Flowable

interface IProfileRemoteSource {
    fun getProfile(username: String): Flowable<ProfileResponse>
    fun getRepository(username: String, page: Int): Flowable<List<RepositoryResponse>>
}