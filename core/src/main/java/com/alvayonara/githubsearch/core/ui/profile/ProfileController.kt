package com.alvayonara.githubsearch.core.ui.profile

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.ui.LoadMoreModel
import javax.inject.Inject

class ProfileController @Inject constructor(private val context: Context) : EpoxyController() {

    private var _profile: Profile? = null
    private val _repository: MutableList<Repository> = mutableListOf()
    private var _isLoadMore = true

    fun setIsLoadMore(isLoadMore: Boolean) {
        this._isLoadMore = isLoadMore
        requestModelBuild()
    }

    fun setProfile(data: Profile) {
        this._profile = data
    }

    fun setRepository(data: MutableList<Repository>) {
        this._repository.clear()
        this._repository.addAll(data)
        requestModelBuild()
    }

    fun addRepository(data: MutableList<Repository>) {
        this._repository.addAll(data)
        requestModelBuild()
    }

    override fun buildModels() {
        this._profile?.let {
            ProfileModel(context, it)
                .id(it.login)
                .addTo(this)
        }

        this._repository.forEach {
            RepositoryModel(context, it)
                .id(it.name)
                .addIf(this._repository.isNotEmpty(), this)
        }

        LoadMoreModel().id("loadMore").addIf(_isLoadMore, this)
    }
}