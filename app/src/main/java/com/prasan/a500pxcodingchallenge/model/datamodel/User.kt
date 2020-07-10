package com.prasan.a500pxcodingchallenge.model.datamodel


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "city")
    val city: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "firstname")
    val firstname: String?,
    @Json(name = "fullname")
    val fullname: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "lastname")
    val lastname: String?,
    @Json(name = "upgrade_status")
    val upgradeStatus: Int?,
    @Json(name = "username")
    val username: String?,
    @Json(name = "userpic_url")
    val userpicUrl: String?
)