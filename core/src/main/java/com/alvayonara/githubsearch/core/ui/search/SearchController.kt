package com.alvayonara.githubsearch.core.ui.search

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.ui.LoadMoreModel
import javax.inject.Inject

class SearchController @Inject constructor(private val context: Context) : EpoxyController() {

    private val _users: MutableList<Profile> = mutableListOf()
    private var _isLoadMore = true

    lateinit var onUserClickCallback: ((String) -> Unit)

    fun setIsLoadMore(isLoadMore: Boolean) {
        this._isLoadMore = isLoadMore
        requestModelBuild()
    }

    fun setUsers(data: MutableList<Profile>) {
        this._users.clear()
        this._users.addAll(data)
        requestModelBuild()
    }

    fun setIsUserEmpty() {
        this._users.clear()
        requestModelBuild()
    }

    fun addUsers(data: MutableList<Profile>) {
        this._users.addAll(data)
        requestModelBuild()
    }

    override fun buildModels() {
        this._users.forEach {
            SearchUserModel(context, it, onUserClickCallback)
                .id(it.login)
                .addIf(_users.isNotEmpty(), this)
        }

        LoadMoreModel().id("loadMore").addIf(_isLoadMore, this)
    }
}