package com.alvayonara.githubsearch.core.data.source.remote.network

import com.alvayonara.githubsearch.core.data.source.remote.response.profile.ProfileResponse
import com.alvayonara.githubsearch.core.data.source.remote.response.profile.RepositoryResponse
import com.alvayonara.githubsearch.core.data.source.remote.response.search.SearchResponse
import com.alvayonara.githubsearch.core.utils.Constant
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = Constant.Services.PER_PAGE
    ): Flowable<SearchResponse>

    @GET("users/{username}")
    fun profile(
        @Path("username") username: String
    ): Flowable<ProfileResponse>

    @GET("users/{username}/repos")
    fun repository(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = Constant.Services.PER_PAGE,
        @Query("sort") sort: String = Constant.Services.SORT,
        @Query("direction") direction: String = Constant.Services.DIRECTION
    ): Flowable<List<RepositoryResponse>>
}