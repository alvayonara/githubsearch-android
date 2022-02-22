package com.alvayonara.githubsearch.core.repository

import com.alvayonara.githubsearch.core.data.source.remote.ProfileRepository
import com.alvayonara.githubsearch.core.data.source.remote.response.ProfileRemoteSource
import com.alvayonara.githubsearch.core.domain.model.search.SearchItem
import com.alvayonara.githubsearch.core.utils.Constant
import com.alvayonara.githubsearch.core.utils.DataDummy
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class ProfileRepositoryTest {

    private val profileRemoteSource: ProfileRemoteSource = mockk()
    private lateinit var profileRepository: ProfileRepository

    @Before
    fun setUp() {
        profileRepository = ProfileRepository(profileRemoteSource)
    }

    /**
     * Get Profile
     */
    @Test
    fun `given success response then return profile`() = runBlocking {
        // given
        val username = DataDummy.getProfile().login
        val fakeResponse = DataDummy.getProfileResponses()
        val expectedUser = DataDummy.getProfile()
        coEvery {
            profileRemoteSource.getProfile(
                username
            )
        } returns fakeResponse

        // when
        val actual = profileRepository.getProfile(username)

        // then
        assertEquals(expectedUser, actual)
    }

    @Test(expected = IOException::class)
    fun `given network is error when get profile then throw IOException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        coEvery {
            profileRemoteSource.getProfile(
                username
            )
        } throws IOException()

        // when
        profileRepository.getProfile(username)
    }

    @Test(expected = HttpException::class)
    fun `given unauthorized network when get profile then throw HttpException`() = runTest {
            // given
            val username = DataDummy.getProfile().login
            val responseUnauthorized =
                Response.error<List<SearchItem>>(
                    HttpURLConnection.HTTP_UNAUTHORIZED,
                    mockk(relaxed = true)
                )
            coEvery {
                profileRemoteSource.getProfile(
                    username
                )
            } throws HttpException(responseUnauthorized)

            // when
            profileRepository.getProfile(username)
        }

    @Test(expected = HttpException::class)
    fun `given forbidden network when get profile then throw HttpException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        val responseForbidden =
            Response.error<List<SearchItem>>(
                HttpURLConnection.HTTP_FORBIDDEN,
                mockk(relaxed = true)
            )
        coEvery {
            profileRemoteSource.getProfile(
                username
            )
        } throws HttpException(responseForbidden)

        // when
        profileRepository.getProfile(username)
    }

    /**
     * Get repository
     */
    @Test
    fun `given success response then return repos`() = runBlocking {
        // given
        val username = DataDummy.getProfile().login
        val fakeResponse = DataDummy.getRepositoryResponses()
        val expectedUser = DataDummy.getRepository()
        coEvery {
            profileRemoteSource.getRepository(
                username,
                Constant.Services.FIRST_PAGE
            )
        } returns fakeResponse

        // when
        val actual = profileRepository.getRepository(username, Constant.Services.FIRST_PAGE)

        // then
        assertEquals(expectedUser, actual)
    }

    @Test(expected = IOException::class)
    fun `given network is error when get repos then throw IOException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        coEvery {
            profileRemoteSource.getRepository(
                username,
                Constant.Services.FIRST_PAGE
            )
        } throws IOException()

        // when
        profileRepository.getRepository(username, Constant.Services.FIRST_PAGE)
    }

    @Test(expected = HttpException::class)
    fun `given unauthorized network when get repos then throw HttpException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        val responseUnauthorized =
            Response.error<List<SearchItem>>(
                HttpURLConnection.HTTP_UNAUTHORIZED,
                mockk(relaxed = true)
            )
        coEvery {
            profileRemoteSource.getRepository(
                username,
                Constant.Services.FIRST_PAGE
            )
        } throws HttpException(responseUnauthorized)

        // when
        profileRepository.getRepository(username, Constant.Services.FIRST_PAGE)
    }

    @Test(expected = HttpException::class)
    fun `given forbidden network when get repos then throw HttpException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        val responseForbidden =
            Response.error<List<SearchItem>>(
                HttpURLConnection.HTTP_FORBIDDEN,
                mockk(relaxed = true)
            )
        coEvery {
            profileRemoteSource.getRepository(
                username,
                Constant.Services.FIRST_PAGE
            )
        } throws HttpException(responseForbidden)

        // when
        profileRepository.getRepository(username, Constant.Services.FIRST_PAGE)
    }
}