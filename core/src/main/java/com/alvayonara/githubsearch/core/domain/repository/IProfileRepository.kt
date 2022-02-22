package com.alvayonara.githubsearch.core.domain.repository

import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository

interface IProfileRepository {
    suspend fun getProfile(username: String): Profile
    suspend fun getRepository(username: String, page: Int): List<Repository>
}