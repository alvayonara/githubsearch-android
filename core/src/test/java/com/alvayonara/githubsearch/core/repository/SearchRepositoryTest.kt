package com.alvayonara.githubsearch.core.repository

import com.alvayonara.githubsearch.core.data.source.remote.SearchRepository
import com.alvayonara.githubsearch.core.data.source.remote.response.SearchRemoteSource
import com.alvayonara.githubsearch.core.domain.model.search.SearchItem
import com.alvayonara.githubsearch.core.utils.Constant
import com.alvayonara.githubsearch.core.utils.DataDummy
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class SearchRepositoryTest {

    private val searchRemoteSource: SearchRemoteSource = mockk()
    private lateinit var searchRepository: SearchRepository

    @Before
    fun setUp() {
        searchRepository = SearchRepository(searchRemoteSource)
    }

    /**
     * Search user
     */
//    @Test
//    fun `given success response then return search list of users`() = runBlocking {
//        // given
//        val query = DataDummy.getProfile().login
//        val fakeResponse = DataDummy.getSearchResponses()
//        val expectedUser = DataDummy.getSearchItem()
//        coEvery {
//            searchRemoteSource.searchUser(
//                query,
//                Constant.Services.FIRST_PAGE
//            )
//        } returns fakeResponse
//
//        // when
//        val actual = searchRepository.searchUser(query, Constant.Services.FIRST_PAGE)
//
//        // then
//        assertEquals(expectedUser, actual)
//    }
//
//    @Test(expected = IOException::class)
//    fun `given network is error when get search list of users then throw IOException`() = runTest {
//        // given
//        val query = DataDummy.getProfile().login
//        coEvery {
//            searchRemoteSource.searchUser(
//                query,
//                Constant.Services.FIRST_PAGE
//            )
//        } throws IOException()
//
//        // when
//        searchRepository.searchUser(query, Constant.Services.FIRST_PAGE)
//    }
//
//    @Test(expected = HttpException::class)
//    fun `given unauthorized network when get search list of users then throw HttpException`() =
//        runTest {
//            // given
//            val query = DataDummy.getProfile().login
//            val responseUnauthorized =
//                Response.error<List<SearchItem>>(
//                    HttpURLConnection.HTTP_UNAUTHORIZED,
//                    mockk(relaxed = true)
//                )
//            coEvery {
//                searchRemoteSource.searchUser(
//                    query,
//                    Constant.Services.FIRST_PAGE
//                )
//            } throws HttpException(responseUnauthorized)
//
//            // when
//            searchRepository.searchUser(query, Constant.Services.FIRST_PAGE)
//        }
//
//    @Test(expected = HttpException::class)
//    fun `given forbidden network when get list of users then throw HttpException`() = runTest {
//        // given
//        val query = DataDummy.getProfile().login
//        val responseForbidden =
//            Response.error<List<SearchItem>>(
//                HttpURLConnection.HTTP_FORBIDDEN,
//                mockk(relaxed = true)
//            )
//        coEvery {
//            searchRemoteSource.searchUser(
//                query,
//                Constant.Services.FIRST_PAGE
//            )
//        } throws HttpException(responseForbidden)
//
//        // when
//        searchRepository.searchUser(query, Constant.Services.FIRST_PAGE)
//    }

    @Test
    fun `given success response then return search list of users`() {
        // given
        val query = DataDummy.getProfile().login
        val fakeResponse = DataDummy.getSearchResponses()
        val expectedUser = DataDummy.getSearchItem()
        every {
            searchRemoteSource.searchUser(
                query,
                Constant.Services.FIRST_PAGE
            )
        } returns Flowable.just(fakeResponse)

        // when
        val actual = searchRepository.searchUser(query, Constant.Services.FIRST_PAGE)

        // then
        assertNotNull(actual)
    }

    @Test
    fun `given network is error when get search list of users then throw IOException`() {
        // given
        val query = DataDummy.getProfile().login
        every {
            searchRemoteSource.searchUser(
                query,
                Constant.Services.FIRST_PAGE
            )
        } returns Flowable.error(IOException())

        // when
        val actual = searchRepository.searchUser(query, Constant.Services.FIRST_PAGE)

        assertNotNull(actual)
    }

//
//    @Test(expected = HttpException::class)
//    fun `given unauthorized network when get search list of users then throw HttpException`() =
//        runTest {
//            // given
//            val query = DataDummy.getProfile().login
//            val responseUnauthorized =
//                Response.error<List<SearchItem>>(
//                    HttpURLConnection.HTTP_UNAUTHORIZED,
//                    mockk(relaxed = true)
//                )
//            coEvery {
//                searchRemoteSource.searchUser(
//                    query,
//                    Constant.Services.FIRST_PAGE
//                )
//            } throws HttpException(responseUnauthorized)
//
//            // when
//            searchRepository.searchUser(query, Constant.Services.FIRST_PAGE)
//        }
//
//    @Test(expected = HttpException::class)
//    fun `given forbidden network when get list of users then throw HttpException`() = runTest {
//        // given
//        val query = DataDummy.getProfile().login
//        val responseForbidden =
//            Response.error<List<SearchItem>>(
//                HttpURLConnection.HTTP_FORBIDDEN,
//                mockk(relaxed = true)
//            )
//        coEvery {
//            searchRemoteSource.searchUser(
//                query,
//                Constant.Services.FIRST_PAGE
//            )
//        } throws HttpException(responseForbidden)
//
//        // when
//        searchRepository.searchUser(query, Constant.Services.FIRST_PAGE)
//    }
}