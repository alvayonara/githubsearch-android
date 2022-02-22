package com.alvayonara.githubsearch.core.ui.search

import android.content.Context
import com.alvayonara.githubsearch.core.R
import com.alvayonara.githubsearch.core.databinding.ItemListSearchUserBinding
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.utils.ViewBindingKotlinModel
import com.alvayonara.githubsearch.core.utils.cacheImage

class SearchUserModel(
    private val context: Context,
    private val user: Profile,
    private val onUserClickCallback: ((String) -> Unit)
) : ViewBindingKotlinModel<ItemListSearchUserBinding>(R.layout.item_list_search_user) {

    override fun ItemListSearchUserBinding.bind() {
        user.apply {
            ivImage.cacheImage(avatarUrl, R.color.alto)
            tvName.text = name
            tvUsername.text = context.getString(R.string.txt_username, login)
            tvBio.text = bio
            tvLocation.text = company
            tvEmail.text = email
            clSearchUser.setOnClickListener { onUserClickCallback.invoke(login) }
        }
    }
}