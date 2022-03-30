package com.alvayonara.githubsearch.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvayonara.githubsearch.GitHubApplication
import com.alvayonara.githubsearch.R
import com.alvayonara.githubsearch.core.base.BaseFragment
import com.alvayonara.githubsearch.core.ui.profile.ProfileController
import com.alvayonara.githubsearch.core.utils.*
import com.alvayonara.githubsearch.databinding.FragmentDetailBinding
import com.alvayonara.githubsearch.di.detail.DetailViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import javax.inject.Inject

@ObsoleteCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    @Inject
    lateinit var factory: DetailViewModelFactory
    private val _detailViewModel: DetailViewModel by viewModels { factory }

    @Inject
    lateinit var profileController: ProfileController

    private val args: DetailFragmentArgs by navArgs()

    private var _currentPage = Constant.Services.FIRST_PAGE
    private var _username = ""
    private var _isScrolled = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    override fun setup() {
        setupView()
        setupRecyclerView()
        subscribeViewModel()
    }

    override fun setupView() {
        args.username.let {
            _username = it
            _detailViewModel.getProfile(it)
        }

        binding.lytError.clError.setOnClickListener {
            binding.lytError.root.gone()
            _detailViewModel.getProfile(_username)
        }
    }

    override fun setupRecyclerView() {
        binding.rvProfile.apply {
            addOnScrollListener(object : RecyclerViewLoadMore() {
                override fun onLoadMore() {
                    if (!_isScrolled) {
                        _isScrolled = true
                        _currentPage++
                    }
                    loadMoreState(true)
                    _detailViewModel.getNextRepository(_username, _currentPage)
                }
            })
            layoutManager = LinearLayoutManager(requireActivity())
            setController(profileController)
        }
    }

    override fun subscribeViewModel() {
        _detailViewModel.apply {
            detail.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is DetailViewModel.DetailUiState.ShowLoading -> showLoading(true)
                    is DetailViewModel.DetailUiState.ProfileResult ->  profileController.setProfile(result.model)
                    is DetailViewModel.DetailUiState.RepositoryResult -> profileController.setRepository(result.model.toMutableList())
                    is DetailViewModel.DetailUiState.ShowContent -> showLoading(false)
                    is DetailViewModel.DetailUiState.ShowError -> {
                        showLoading(false)
                        result.let { throwable ->
                            showError(throwable.error)
                            binding.sbProfile.showErrorSnackbar(
                                text = getString(R.string.txt_error, throwable.error.getThrowable()),
                                onRetry = {
                                    _detailViewModel.getProfile(_username)
                                }
                            )
                        }
                    }
                }
            }

            repository.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is DetailViewModel.RepositoryUiState.RepositoryResult -> {
                        _isScrolled = false
                        result.model.let {
                            if (it.isEmpty()) loadMoreState(false)
                            profileController.addRepository(it.toMutableList())
                        }
                    }
                    is DetailViewModel.RepositoryUiState.ShowError -> {
                        loadMoreState(false)
                        result.error.let { throwable ->
                            binding.sbProfile.showErrorSnackbar(
                                text = getString(R.string.txt_error, throwable.getThrowable()),
                                onRetry = {
                                    _detailViewModel.getNextRepository(_username, _currentPage)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun inject() {
        (requireActivity().application as GitHubApplication).appComponent.detailComponent().create().inject(this)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.rvProfile.gone()
            binding.pbProfile.visible()
        } else {
            binding.pbProfile.gone()
            binding.rvProfile.visible()
        }
    }

    private fun showError(throwable: Throwable) {
        binding.lytError.apply {
            tvSubtitleError.text = getString(R.string.txt_error, throwable.getThrowable())
            root.visible()
        }
    }

    private fun loadMoreState(isLoadMore: Boolean) {
        profileController.setIsLoadMore(isLoadMore)
    }
}