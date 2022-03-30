package com.alvayonara.githubsearch.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.usecase.profile.ProfileUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SearchViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _compositeDisposable by lazy { CompositeDisposable() }

    private val _search: MutableLiveData<SearchUiState> = MutableLiveData()
    val search: MutableLiveData<SearchUiState> get() = _search

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

    init {
        search("Alva")
    }

    fun search(name: String) {
        _compositeDisposable.add(profileUseCase.getProfile(name)
            .doOnSubscribe { _search.postValue(SearchUiState.ShowLoading) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe({
                _search.postValue(SearchUiState.SearchResult(listOf(it)))
            }, {
                _search.postValue(SearchUiState.ShowError(it))
            })
        )
    }

    sealed class SearchUiState {
        object ShowLoading : SearchUiState()
        data class ShowError(val error: Throwable) : SearchUiState()
        data class SearchResult(val model: List<Profile>) : SearchUiState()
    }

    override fun onCleared() {
        super.onCleared()
        _compositeDisposable.clear()
    }
}