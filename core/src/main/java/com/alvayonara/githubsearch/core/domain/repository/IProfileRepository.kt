package com.alvayonara.githubsearch.core.domain.repository

import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import io.reactivex.rxjava3.core.Flowable

interface IProfileRepository {
    fun getProfile(username: String): Flowable<Profile>
    fun getRepository(username: String, page: Int): Flowable<List<Repository>>
}