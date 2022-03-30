package com.alvayonara.githubsearch.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.domain.usecase.profile.ProfileUseCase
import com.alvayonara.githubsearch.core.utils.Constant
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DetailViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) :
    ViewModel() {

    private val _compositeDisposable by lazy { CompositeDisposable() }

    private val _detail: MutableLiveData<DetailUiState> =
        MutableLiveData()
    val detail: MutableLiveData<DetailUiState> get() = _detail

    private val _repository: MutableLiveData<RepositoryUiState> = MutableLiveData()
    val repository: MutableLiveData<RepositoryUiState> = _repository

    fun getProfile(name: String) {
        _compositeDisposable.add(profileUseCase.getProfile(name)
            .doOnSubscribe { _detail.postValue(DetailUiState.ShowLoading) }
            .doOnNext { _detail.postValue(DetailUiState.ProfileResult(it)) }
            .subscribeOn(Schedulers.io())
            .flatMap {
                profileUseCase.getRepository(name, Constant.Services.FIRST_PAGE)
                    .doOnNext { _detail.postValue(DetailUiState.RepositoryResult(it)) }
            }
            .observeOn(Schedulers.computation())
            .subscribe({
                _detail.postValue(DetailUiState.ShowContent)
            }, {
                _detail.postValue(DetailUiState.ShowError(it))
            })
        )
    }

    fun getNextRepository(username: String, page: Int) {
        _compositeDisposable.add(
            profileUseCase.getRepository(username, page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe({
                    _repository.postValue(RepositoryUiState.RepositoryResult(it))
                }, {
                    _repository.postValue(RepositoryUiState.ShowError(it))
                })
        )
    }

    sealed class DetailUiState {
        object ShowContent : DetailUiState()
        object ShowLoading : DetailUiState()
        data class ShowError(val error: Throwable) : DetailUiState()
        data class ProfileResult(val model: Profile) : DetailUiState()
        data class RepositoryResult(val model: List<Repository>) : DetailUiState()
    }

    sealed class RepositoryUiState {
        data class ShowError(val error: Throwable) : RepositoryUiState()
        data class RepositoryResult(val model: List<Repository>) : RepositoryUiState()
    }

    override fun onCleared() {
        super.onCleared()
        _compositeDisposable.clear()
    }
}