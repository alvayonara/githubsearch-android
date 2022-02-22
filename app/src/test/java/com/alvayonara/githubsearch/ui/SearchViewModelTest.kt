package com.alvayonara.githubsearch.ui

import androidx.lifecycle.Observer
import com.alvayonara.githubsearch.core.data.source.remote.Resource
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.usecase.profile.ProfileUseCase
import com.alvayonara.githubsearch.core.domain.usecase.search.SearchUseCase
import com.alvayonara.githubsearch.core.utils.DataDummy
import com.alvayonara.githubsearch.ui.search.SearchViewModel
import com.alvayonara.githubsearch.utils.BaseUnitTest
import com.alvayonara.githubsearch.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@FlowPreview
class SearchViewModelTest: BaseUnitTest() {

    private val searchUseCase: SearchUseCase = mockk()
    private val profileUseCase: ProfileUseCase = mockk()

    private lateinit var searchViewModel: SearchViewModel

    private val observerSearch: Observer<Resource<List<Profile>>> = mockk(relaxUnitFun = true)

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(searchUseCase, profileUseCase)
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
        coEvery { searchUseCase.searchUser(any()) } returns flowOf(searchResult)
        coEvery { profileUseCase.getProfile(any()) } returns flowOf(profileResult)

        // when
        searchViewModel.search(searchResult.first().login)

        // then
        assertNotNull(searchViewModel.search.getOrAwaitValue())
        assertEquals(Resource.success(listOf(profileResult)), searchViewModel.search.getOrAwaitValue())
    }

    @Test
    fun `given throwable response when get search list of users should return result`() {
        // given
        val searchResult = DataDummy.getSearchItem()
        coEvery { searchUseCase.searchUser(any()) } throws IOException()
        coEvery { profileUseCase.getProfile(any()) } throws IOException()

        // when
        searchViewModel.search(searchResult.first().login)

        // then
        assertNotNull(searchViewModel.search.getOrAwaitValue())
    }
}