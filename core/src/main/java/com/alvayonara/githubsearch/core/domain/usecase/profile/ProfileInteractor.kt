package com.alvayonara.githubsearch.core.domain.usecase.profile

import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileInteractor @Inject constructor(private val profileRepository: IProfileRepository) :
    ProfileUseCase {
    override suspend fun getProfile(username: String): Flow<Profile> =
        flow { emit(profileRepository.getProfile(username)) }

    override suspend fun getRepository(username: String, page: Int): Flow<List<Repository>> =
        flow {
            emit(profileRepository.getRepository(username, page))
        }
}