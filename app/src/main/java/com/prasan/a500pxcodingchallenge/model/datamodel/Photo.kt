package com.prasan.a500pxcodingchallenge.model.datamodel


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = "category")
    val category: Int,
    @Json(name = "comments_count")
    val commentsCount: Int,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_url")
    val imageUrl: List<String>,
    @Json(name = "name")
    val name: String,
    @Json(name = "nsfw")
    val nsfw: Boolean,
    @Json(name = "privacy")
    val privacy: Boolean,
    @Json(name = "rating")
    val rating: Double,
    @Json(name = "times_viewed")
    val timesViewed: Int,
    @Json(name = "user")
    val user: User,
    @Json(name = "votes_count")
    val votesCount: Int,
    @Json(name = "width")
    val width: Int
)