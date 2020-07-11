package com.prasan.a500pxcodingchallenge.model.datamodel


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "about")
    val about: String,
    @Json(name = "active")
    val active: Int,
    @Json(name = "affection")
    val affection: Int,
    @Json(name = "avatar_version")
    val avatarVersion: Int,
    @Json(name = "avatars")
    val avatars: Avatars,
    @Json(name = "city")
    val city: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "cover_url")
    val coverUrl: String?,
    @Json(name = "firstname")
    val firstname: String,
    @Json(name = "followers_count")
    val followersCount: Int,
    @Json(name = "following")
    val following: Boolean,
    @Json(name = "fullname")
    val fullname: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "lastname")
    val lastname: String,
    @Json(name = "registration_date")
    val registrationDate: String,
    @Json(name = "state")
    val state: String?,
    @Json(name = "upgrade_status")
    val upgradeStatus: Int,
    @Json(name = "username")
    val username: String,
    @Json(name = "userpic_https_url")
    val userpicHttpsUrl: String,
    @Json(name = "userpic_url")
    val userpicUrl: String,
    @Json(name = "usertype")
    val usertype: Int
)