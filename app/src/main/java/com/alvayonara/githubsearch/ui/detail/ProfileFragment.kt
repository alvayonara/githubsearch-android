package com.alvayonara.githubsearch.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvayonara.githubsearch.GitHubApplication
import com.alvayonara.githubsearch.R
import com.alvayonara.githubsearch.core.base.BaseFragment
import com.alvayonara.githubsearch.core.data.source.remote.Resource
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.ui.profile.ProfileController
import com.alvayonara.githubsearch.core.utils.*
import com.alvayonara.githubsearch.databinding.FragmentProfileBinding
import com.alvayonara.githubsearch.ui.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import javax.inject.Inject

@ObsoleteCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val _profileViewModel: ProfileViewModel by viewModels { factory }

    @Inject
    lateinit var profileController: ProfileController

    private val args: ProfileFragmentArgs by navArgs()

    private var _currentPage = Constant.Services.FIRST_PAGE
    private var _username = ""
    private var _isScrolled = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override fun setup() {
        setupView()
        setupRecyclerView()
        subscribeViewModel()
    }

    override fun setupView() {
        args.username.let {
            _username = it
            _profileViewModel.getProfile(it)
        }

        binding.lytError.clError.setOnClickListener {
            binding.lytError.root.gone()
            _profileViewModel.getProfile(_username)
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
                    _profileViewModel.getNextRepository(_username, _currentPage)
                }
            })
            layoutManager = LinearLayoutManager(requireActivity())
            setController(profileController)
        }
    }

    override fun subscribeViewModel() {
        _profileViewModel.apply {
            loading.observe(viewLifecycleOwner) { showLoading(it) }

            profile.observe(viewLifecycleOwner) { result ->
                when (result.status) {
                    Resource.Status.SUCCESS -> {
                        showLoading(false)
                        result.body?.let {
                            it.forEach { data ->
                                when (data.first) {
                                    Constant.DetailProfile.PROFILE -> profileController.setProfile(
                                        data.second as Profile
                                    )
                                    Constant.DetailProfile.REPOSITORY -> profileController.setRepository(
                                        (data.second as List<Repository>).toMutableList()
                                    )
                                }
                            }
                        }
                    }
                    Resource.Status.ERROR -> {
                        showLoading(false)
                        result.throwable?.let { throwable ->
                            showError(throwable)
                            binding.sbProfile.showErrorSnackbar(
                                text = getString(R.string.txt_error, throwable.getThrowable()),
                                onRetry = {
                                    _profileViewModel.getProfile(_username)
                                }
                            )
                        }
                    }
                }
            }

            repository.observe(viewLifecycleOwner) { result ->
                when (result.status) {
                    Resource.Status.SUCCESS -> {
                        _isScrolled = false
                        result.body?.let {
                            if (it.isEmpty()) loadMoreState(false)
                            profileController.addRepository(it.toMutableList())
                        }
                    }
                    Resource.Status.ERROR -> {
                        loadMoreState(false)
                        result.throwable?.let { throwable ->
                            binding.sbProfile.showErrorSnackbar(
                                text = getString(R.string.txt_error, throwable.getThrowable()),
                                onRetry = {
                                    _profileViewModel.getNextRepository(_username, _currentPage)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun inject() {
        (requireActivity().application as GitHubApplication).appComponent.inject(this)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbProfile.visible()
        } else {
            binding.pbProfile.gone()
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