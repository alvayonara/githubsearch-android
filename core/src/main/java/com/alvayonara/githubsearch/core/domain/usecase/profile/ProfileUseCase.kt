package com.alvayonara.githubsearch.core.domain.usecase.profile

import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

interface ProfileUseCase {
    fun getProfile(username: String): Flowable<Profile>
    fun getRepository(username: String, page: Int): Flowable<List<Repository>>
}