package com.alvayonara.githubsearch.ui.detail

import androidx.lifecycle.*
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.domain.usecase.profile.ProfileUseCase
import com.alvayonara.githubsearch.core.utils.Constant
import com.alvayonara.githubsearch.core.data.source.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProfileViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) :
    ViewModel() {

    private val _profile: MutableLiveData<Resource<List<Pair<Constant.DetailProfile, Any>>>> =
        MutableLiveData()
    val profile: MutableLiveData<Resource<List<Pair<Constant.DetailProfile, Any>>>> get() = _profile

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: MutableLiveData<Boolean> get() = _loading

    private val _repository: MutableLiveData<Resource<List<Repository>>> = MutableLiveData()
    val repository: MutableLiveData<Resource<List<Repository>>> = _repository

    fun getProfile(name: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            val result = mutableListOf<Pair<Constant.DetailProfile, Any>>()
            profileUseCase.getProfile(name)
                .flatMapLatest { profile ->
                    result.add(Constant.DetailProfile.PROFILE to profile)
                    profileUseCase.getRepository(name, Constant.Services.FIRST_PAGE)
                }
                .flowOn(Dispatchers.Default)
                .catch { _profile.postValue(Resource.error(it)) }
                .collect { repository ->
                    result.add(Constant.DetailProfile.REPOSITORY to repository)
                    _profile.postValue(Resource.success(result))
                }
        }
    }

    fun getNextRepository(username: String, page: Int) {
        viewModelScope.launch {
            profileUseCase.getRepository(username, page)
                .catch { _repository.postValue(Resource.error(it)) }
                .collect { _repository.postValue(Resource.success(it)) }
        }
    }
}