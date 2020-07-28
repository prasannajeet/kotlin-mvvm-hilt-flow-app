package com.prasan.kotlinmvvmhiltflowapp.data.datamodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Avatars(
    @Json(name = "default")
    val default: Default,
    @Json(name = "large")
    val large: Large,
    @Json(name = "small")
    val small: Small,
    @Json(name = "tiny")
    val tiny: Tiny
)