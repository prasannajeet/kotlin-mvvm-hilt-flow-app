package com.prasan.a500pxcodingchallenge.model.datamodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tiny(
    @Json(name = "https")
    val https: String
)