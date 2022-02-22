package com.alvayonara.githubsearch.ui

import androidx.lifecycle.Observer
import com.alvayonara.githubsearch.core.data.source.remote.Resource
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.domain.usecase.profile.ProfileUseCase
import com.alvayonara.githubsearch.core.utils.Constant
import com.alvayonara.githubsearch.core.utils.DataDummy
import com.alvayonara.githubsearch.ui.detail.ProfileViewModel
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
class ProfileViewModelTest: BaseUnitTest() {

    private val profileUseCase: ProfileUseCase = mockk()

    private lateinit var profileViewModel: ProfileViewModel

    private val observerProfile: Observer<Resource<List<Pair<Constant.DetailProfile, Any>>>> = mockk(relaxUnitFun = true)
    private val observerRepository: Observer<Resource<List<Repository>>> = mockk(relaxUnitFun = true)

    @Before
    fun setUp() {
        profileViewModel = ProfileViewModel(profileUseCase)
        profileViewModel.profile.observeForever(observerProfile)
        profileViewModel.repository.observeForever(observerRepository)
    }

    @After
    fun finish() {
        profileViewModel.profile.removeObserver(observerProfile)
        profileViewModel.repository.removeObserver(observerRepository)
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
        profileViewModel.getProfile(profileResult.login)

        // then
        assertNotNull(profileViewModel.profile.getOrAwaitValue())
        assertEquals(
            Resource.success(DataDummy.getDetailProfile()),
            profileViewModel.profile.getOrAwaitValue()
        )
    }

    @Test
    fun `given throwable response when get profile should return result`() {
        // given
        val profileResult = DataDummy.getProfile()
        coEvery { profileUseCase.getProfile(any()) } throws IOException()
        coEvery { profileUseCase.getRepository(any(), Constant.Services.FIRST_PAGE) } throws IOException()

        // when
        profileViewModel.getProfile(profileResult.login)
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
        profileViewModel.getNextRepository(DataDummy.getProfile().login, Constant.Services.FIRST_PAGE)

        // then
        assertNotNull(profileViewModel.repository.getOrAwaitValue())
        assertEquals(
            Resource.success(DataDummy.getRepository()),
            profileViewModel.repository.getOrAwaitValue()
        )
    }

    @Test
    fun `given throwable response when get repos should return result`() {
        // given
        coEvery { profileUseCase.getRepository(any(), Constant.Services.FIRST_PAGE) } throws IOException()

        // when
        profileViewModel.getNextRepository(DataDummy.getProfile().login, Constant.Services.FIRST_PAGE)
    }
}