package com.example.jetpack.ktpaging

import androidx.recyclerview.widget.DiffUtil

/**
 * 上层用到的实体
 */
data class GitHubAccount(
    var login: String? = null,
    var id: Int = 0,
    var node_id: String? = null,
    var avatar_url: String? = null,
    var gravatar_id: String? = null,
    var url: String? = null,
    var html_url: String? = null,
    var followers_url: String? = null,
    var following_url: String? = null,
    var gists_url: String? = null,
    var starred_url: String? = null,
    var subscriptions_url: String? = null,
    var organizations_url: String? = null,
    var repos_url: String? = null,
    var events_url: String? = null,
    var received_events_url: String? = null,
    var type: String? = null,
    var isSite_admin: Boolean = false
) {
    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<GitHubAccount>() {
            override fun areItemsTheSame(oldItem: GitHubAccount, newItem: GitHubAccount): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GitHubAccount,
                newItem: GitHubAccount
            ): Boolean =
                oldItem == newItem
        }
    }
}