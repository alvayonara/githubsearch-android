package com.alvayonara.githubsearch.ui

import androidx.lifecycle.Observer
import com.alvayonara.githubsearch.core.domain.usecase.profile.ProfileUseCase
import com.alvayonara.githubsearch.core.utils.DataDummy
import com.alvayonara.githubsearch.ui.search.SearchViewModel
import com.alvayonara.githubsearch.utils.BaseUnitTest
import com.alvayonara.githubsearch.utils.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Flowable
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@FlowPreview
class SearchViewModelTest: BaseUnitTest() {

    private val profileUseCase: ProfileUseCase = mockk(relaxed = true)

    private lateinit var searchViewModel: SearchViewModel

    private val observerSearch: Observer<SearchViewModel.SearchUiState> = mockk(relaxUnitFun = true)

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(profileUseCase)
        searchViewModel.search.observeForever(observerSearch)
    }

    @After
    fun finish() {
        searchViewModel.search.removeObserver(observerSearch)
    }

    /**
     * Search
     */
    @Test
    fun `given success response when get search list of users should return result`() {
        // given
        val searchResult = DataDummy.getSearchItem()
        val profileResult = DataDummy.getProfile()
        every { profileUseCase.getProfile(any()) } returns Flowable.just(profileResult)

        // when
        searchViewModel.search(searchResult.first().login)

        // then
        assertNotNull(searchViewModel.search.getOrAwaitValue())
        assertEquals(SearchViewModel.SearchUiState.SearchResult(listOf(profileResult)), searchViewModel.search.getOrAwaitValue())
    }

    @Test
    fun `given throwable response when get search list of users should return result`() {
        // given
        val searchResult = DataDummy.getSearchItem()
        every { profileUseCase.getProfile(any()) } returns Flowable.error(IOException())

        // when
        searchViewModel.search(searchResult.first().login)

        // then
        assertNotNull(searchViewModel.search.getOrAwaitValue())
    }
}