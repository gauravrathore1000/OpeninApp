package com.example.openinapp.Model.dashboardAPIResponse

data class RecentLink(
    val app: String,
    val created_at: String,
    val domain_id: String,
    val is_favourite: Boolean,
    val original_image: String,
    val smart_link: String,
    val thumbnail: Any,
    val times_ago: String,
    val title: String,
    val total_clicks: Int,
    val url_id: Int,
    val url_prefix: Any,
    val url_suffix: String,
    val web_link: String
)