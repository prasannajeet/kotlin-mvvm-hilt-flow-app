package com.prasan.kotlinmvvmhiltflowapp.data.datamodel


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoResponse(
    @Json(name = "current_page")
    val currentPage: Int,
    @Json(name = "feature")
    val feature: String,
    @Json(name = "filters")
    val filters: Filters,
    @Json(name = "photos")
    val photos: List<Photo>,
    @Json(name = "total_items")
    val totalItems: Int,
    @Json(name = "total_pages")
    val totalPages: Int
)