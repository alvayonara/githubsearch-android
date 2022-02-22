package com.alvayonara.githubsearch.core.ui.profile

import android.content.Context
import com.alvayonara.githubsearch.core.R
import com.alvayonara.githubsearch.core.databinding.ItemListRepositoryBinding
import com.alvayonara.githubsearch.core.domain.model.profile.Repository
import com.alvayonara.githubsearch.core.utils.Constant
import com.alvayonara.githubsearch.core.utils.ViewBindingKotlinModel
import com.alvayonara.githubsearch.core.utils.cacheImage
import com.alvayonara.githubsearch.core.utils.dateTimeConvert

class RepositoryModel(
    private val context: Context,
    private val repository: Repository
) : ViewBindingKotlinModel<ItemListRepositoryBinding>(R.layout.item_list_repository) {

    override fun ItemListRepositoryBinding.bind() {
        repository.apply {
            owner?.let { ivImage.cacheImage(it.avatarUrl, R.color.alto) }
            tvRepository.text = name
            tvDescription.text = description
            tvStar.text = stargazersCount.toString()
            tvUpdatedAt.text = context.getString(
                R.string.txt_updated_at, updatedAt.dateTimeConvert(
                    Constant.DateFormat.FORMAT_DATE_TIMEZONE,
                    Constant.DateFormat.FORMAT_DATE_MMM_DD_YYYY
                )
            )
        }
    }
}