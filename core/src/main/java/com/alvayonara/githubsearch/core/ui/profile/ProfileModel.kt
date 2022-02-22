package com.alvayonara.githubsearch.core.ui.profile

import android.content.Context
import com.alvayonara.githubsearch.core.R
import com.alvayonara.githubsearch.core.databinding.ItemListProfileBinding
import com.alvayonara.githubsearch.core.domain.model.profile.Profile
import com.alvayonara.githubsearch.core.utils.ViewBindingKotlinModel
import com.alvayonara.githubsearch.core.utils.cacheImage

class ProfileModel(
    private val context: Context,
    private val profile: Profile
) : ViewBindingKotlinModel<ItemListProfileBinding>(R.layout.item_list_profile) {

    override fun ItemListProfileBinding.bind() {
        profile.apply {
            ivImage.cacheImage(avatarUrl, R.color.alto)
            tvName.text = name
            tvUsername.text = context.getString(R.string.txt_username, login)
            tvBio.text = bio
            tvFollowers.text = context.getString(R.string.txt_followers, followers)
            tvFollowing.text = context.getString(R.string.txt_following, following)
            tvLocation.text = company
            tvEmail.text = email
        }
    }
}