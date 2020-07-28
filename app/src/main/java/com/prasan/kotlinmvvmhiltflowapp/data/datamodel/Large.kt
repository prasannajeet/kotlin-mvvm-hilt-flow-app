package com.prasan.kotlinmvvmhiltflowapp.data.datamodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Large(
    @Json(name = "https")
    val https: String
)