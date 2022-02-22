package com.alvayonara.githubsearch.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alvayonara.githubsearch.core.data.source.remote.Resource
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.usecase.profile.ProfileUseCase
import com.alvayonara.githubsearch.core.domain.usecase.search.SearchUseCase
import com.alvayonara.githubsearch.core.utils.Constant
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: MutableLiveData<Boolean> get() = _loading

    private val _search: MutableLiveData<Resource<List<Profile>>> = MutableLiveData()
    val search: MutableLiveData<Resource<List<Profile>>> get() = _search

    private val _searchNext: MutableLiveData<Resource<List<Profile>>> = MutableLiveData()
    val searchNext: MutableLiveData<Resource<List<Profile>>> get() = _searchNext

    private val _mutableName: MutableLiveData<String> = MutableLiveData()
    val mutableName: MutableLiveData<String> get() = _mutableName

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val searchQuery = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .mapLatest {
            _mutableName.value = it
            search(it)
        }
        .asLiveData()

    fun search(name: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val result = searchUseCase.searchUser(name, Constant.Services.FIRST_PAGE)
                    .flatMapConcat { it.asFlow() }
                    .flatMapMerge { searchItem ->
                        profileUseCase.getProfile(searchItem.login)
                    }
                    .flowOn(Dispatchers.Default)
                    .toList(mutableListOf())
                _search.postValue(Resource.success(result))
            } catch (throwable: Throwable) {
                _search.postValue(Resource.error(throwable))
            }
        }
    }

    fun searchNext(name: String, page: Int) {
        viewModelScope.launch {
            try {
                val result = searchUseCase.searchUser(name, page)
                    .flatMapConcat { it.asFlow() }
                    .flatMapMerge { searchItem ->
                        profileUseCase.getProfile(searchItem.login)
                    }
                    .flowOn(Dispatchers.Default)
                    .toList(mutableListOf())
                _searchNext.postValue(Resource.success(result))
            } catch (throwable: Throwable) {
                _searchNext.postValue(Resource.error(throwable))
            }
        }
    }
}