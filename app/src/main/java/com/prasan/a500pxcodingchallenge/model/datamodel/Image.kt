package com.prasan.a500pxcodingchallenge.model.datamodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "format")
    val format: String,
    @Json(name = "https_url")
    val httpsUrl: String,
    @Json(name = "size")
    val size: Int,
    @Json(name = "url")
    val url: String
)