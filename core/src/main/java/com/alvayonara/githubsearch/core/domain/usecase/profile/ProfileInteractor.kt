package com.alvayonara.githubsearch.core.domain.usecase.profile

import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.domain.repository.IProfileRepository
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileInteractor @Inject constructor(private val profileRepository: IProfileRepository) :
    ProfileUseCase {
    override fun getProfile(username: String): Flowable<Profile> =
        profileRepository.getProfile(username)

    override fun getRepository(username: String, page: Int): Flowable<List<Repository>> =
        profileRepository.getRepository(username, page)
}