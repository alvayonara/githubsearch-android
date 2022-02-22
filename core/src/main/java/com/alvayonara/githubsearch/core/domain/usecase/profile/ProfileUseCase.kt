package com.alvayonara.githubsearch.core.domain.usecase.profile

import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import kotlinx.coroutines.flow.Flow

interface ProfileUseCase {
    suspend fun getProfile(username: String): Flow<Profile>
    suspend fun getRepository(username: String, page: Int): Flow<List<Repository>>
}