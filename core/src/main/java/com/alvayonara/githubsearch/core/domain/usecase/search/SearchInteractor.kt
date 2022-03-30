package com.alvayonara.githubsearch.core.domain.usecase.search

import com.alvayonara.githubsearch.core.domain.model.search.SearchItem
import com.alvayonara.githubsearch.core.domain.repository.ISearchRepository
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchInteractor @Inject constructor(
    private val searchRepository: ISearchRepository
) :
    SearchUseCase {
    override fun searchUser(query: String, page: Int): Flowable<List<SearchItem>> =
        searchRepository.searchUser(query, page)
}