package com.alvayonara.githubsearch.ui.search

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvayonara.githubsearch.GitHubApplication
import com.alvayonara.githubsearch.R
import com.alvayonara.githubsearch.core.base.BaseFragment
import com.alvayonara.githubsearch.core.data.source.remote.Resource
import com.alvayonara.githubsearch.core.ui.search.SearchController
import com.alvayonara.githubsearch.core.utils.*
import com.alvayonara.githubsearch.databinding.FragmentSearchBinding
import com.alvayonara.githubsearch.di.ViewModelFactory
import com.alvayonara.githubsearch.di.search.SearchViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@FlowPreview
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    @Inject
    lateinit var factory: SearchViewModelFactory
    private val _searchViewModel: SearchViewModel by viewModels { factory }

    @Inject
    lateinit var searchController: SearchController

    private var _currentPage = Constant.Services.FIRST_PAGE
    private var _username = ""
    private var _isScrolled = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun setup() {
        setupView()
        setupRecyclerView()
        subscribeViewModel()
    }

    override fun setupView() {
        binding.apply {
            val searchStream = object : TextStream() {
                override fun onChanged(text: String) {
                    lifecycleScope.launch {
                        _searchViewModel.queryChannel.send(text)
                    }
                }
            }
            lytSearch.edtSearch.addTextChangedListener(searchStream)
            lytSearch.edtSearch.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || keyEvent.action == KeyEvent.ACTION_DOWN
                    || keyEvent.action == KeyEvent.KEYCODE_ENTER
                ) {
                    hideKeyboard(requireActivity())
                    return@OnEditorActionListener true
                }
                false
            })

            lytError.clError.setOnClickListener {
                binding.lytError.root.gone()
                _searchViewModel.search(_username)
            }
        }
    }

    override fun setupRecyclerView() {
        searchController.onUserClickCallback = {
            val nav = SearchFragmentDirections.actionSearchFragmentToProfileFragment(it)
            navigate(nav)
        }

        binding.rvSearch.apply {
            addOnScrollListener(object : RecyclerViewLoadMore() {
                override fun onLoadMore() {
                    if (!_isScrolled) {
                        _isScrolled = true
                        _currentPage++
                    }
                    loadMoreState(true)
                    _searchViewModel.searchNext(_username, _currentPage)
                }
            })
            layoutManager = LinearLayoutManager(requireActivity())
            setController(searchController)
        }
    }

    override fun subscribeViewModel() {
        _searchViewModel.apply {
            searchQuery.observe(viewLifecycleOwner) { }

            mutableName.observe(viewLifecycleOwner) { username ->
                _username = username
            }

            loading.observe(viewLifecycleOwner) { showLoading(it) }

            search.observe(viewLifecycleOwner) { result ->
                when (result.status) {
                    Resource.Status.SUCCESS -> {
                        showLoading(false)
                        result.body?.let {
                            if (it.isNotEmpty()) {
                                searchController.setUsers(it.toMutableList())
                            } else {
                                searchController.setIsUserEmpty()
                            }
                        }
                    }
                    Resource.Status.ERROR -> {
                        showLoading(false)
                        result.throwable?.let { throwable ->
                            showError(throwable)
                            binding.sbSearch.showErrorSnackbar(
                                text = getString(R.string.txt_error, throwable.getThrowable()),
                                onRetry = {
                                    _searchViewModel.search(_username)
                                }
                            )
                        }
                    }
                }
            }

            searchNext.observe(viewLifecycleOwner) { result ->
                when (result.status) {
                    Resource.Status.SUCCESS -> {
                        _isScrolled = false
                        result.body?.let {
                            if (it.isEmpty()) loadMoreState(false)
                            searchController.addUsers(it.toMutableList())
                        }
                    }
                    Resource.Status.ERROR -> {
                        loadMoreState(false)
                        result.throwable?.let { throwable ->
                            binding.sbSearch.showErrorSnackbar(
                                text = getString(R.string.txt_error, throwable.getThrowable()),
                                onRetry = {
                                    _searchViewModel.searchNext(_username, _currentPage)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun inject() {
        (requireActivity().application as GitHubApplication).appComponent.searchComponent().create().inject(this)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.rvSearch.gone()
            binding.pbSearch.visible()
        } else {
            binding.pbSearch.gone()
            binding.rvSearch.visible()
        }
    }

    private fun showError(throwable: Throwable) {
        binding.lytError.apply {
            tvSubtitleError.text = getString(R.string.txt_error, throwable.getThrowable())
            root.visible()
        }
    }

    private fun loadMoreState(isLoadMore: Boolean) {
        searchController.setIsLoadMore(isLoadMore)
    }
}