package com.alvayonara.githubsearch.ui

import androidx.lifecycle.Observer
import com.alvayonara.githubsearch.core.data.source.remote.Resource
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.domain.usecase.profile.ProfileUseCase
import com.alvayonara.githubsearch.core.utils.Constant
import com.alvayonara.githubsearch.core.utils.DataDummy
import com.alvayonara.githubsearch.ui.detail.DetailViewModel
import com.alvayonara.githubsearch.utils.BaseUnitTest
import com.alvayonara.githubsearch.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class DetailViewModelTest: BaseUnitTest() {

    private val profileUseCase: ProfileUseCase = mockk()

    private lateinit var detailViewModel: DetailViewModel

    private val observerProfile: Observer<Resource<List<Pair<Constant.DetailProfile, Any>>>> = mockk(relaxUnitFun = true)
    private val observerRepository: Observer<Resource<List<Repository>>> = mockk(relaxUnitFun = true)

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(profileUseCase)
        detailViewModel.profile.observeForever(observerProfile)
        detailViewModel.repository.observeForever(observerRepository)
    }

    @After
    fun finish() {
        detailViewModel.profile.removeObserver(observerProfile)
        detailViewModel.repository.removeObserver(observerRepository)
    }

    /**
     * Profile
     */
    @Test
    fun `given success response when get profile should return result`() {
        // given
        val profileResult = DataDummy.getProfile()
        val repositoryResult = DataDummy.getRepository()
        coEvery { profileUseCase.getProfile(any()) } returns flowOf(profileResult)
        coEvery { profileUseCase.getRepository(any(), Constant.Services.FIRST_PAGE) } returns flowOf(repositoryResult)

        // when
        detailViewModel.getProfile(profileResult.login)

        // then
        assertNotNull(detailViewModel.profile.getOrAwaitValue())
        assertEquals(
            Resource.success(DataDummy.getDetailProfile()),
            detailViewModel.profile.getOrAwaitValue()
        )
    }

    @Test
    fun `given throwable response when get profile should return result`() {
        // given
        val profileResult = DataDummy.getProfile()
        coEvery { profileUseCase.getProfile(any()) } throws IOException()
        coEvery { profileUseCase.getRepository(any(), Constant.Services.FIRST_PAGE) } throws IOException()

        // when
        detailViewModel.getProfile(profileResult.login)
    }

    /**
     * Repos
     */
    @Test
    fun `given success response when get repos should return result`() {
        // given
        val repositoryResult = DataDummy.getRepository()
        coEvery { profileUseCase.getRepository(any(), Constant.Services.FIRST_PAGE) } returns flowOf(repositoryResult)

        // when
        detailViewModel.getNextRepository(DataDummy.getProfile().login, Constant.Services.FIRST_PAGE)

        // then
        assertNotNull(detailViewModel.repository.getOrAwaitValue())
        assertEquals(
            Resource.success(DataDummy.getRepository()),
            detailViewModel.repository.getOrAwaitValue()
        )
    }

    @Test
    fun `given throwable response when get repos should return result`() {
        // given
        coEvery { profileUseCase.getRepository(any(), Constant.Services.FIRST_PAGE) } throws IOException()

        // when
        detailViewModel.getNextRepository(DataDummy.getProfile().login, Constant.Services.FIRST_PAGE)
    }
}