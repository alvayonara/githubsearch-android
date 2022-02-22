package com.alvayonara.githubsearch.core.domain.usecase.search

import com.alvayonara.githubsearch.core.domain.model.search.SearchItem
import com.alvayonara.githubsearch.core.utils.Constant
import kotlinx.coroutines.flow.Flow

interface SearchUseCase {
    suspend fun searchUser(
        query: String,
        page: Int = Constant.Services.FIRST_PAGE
    ): Flow<List<SearchItem>>
}