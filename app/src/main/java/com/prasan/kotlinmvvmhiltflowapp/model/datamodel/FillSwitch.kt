package com.prasan.kotlinmvvmhiltflowapp.model.datamodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FillSwitch(
    @Json(name = "access_deleted")
    val accessDeleted: Boolean,
    @Json(name = "access_private")
    val accessPrivate: Boolean,
    @Json(name = "always_exclude_nude")
    val alwaysExcludeNude: Boolean,
    @Json(name = "current_user_id")
    val currentUserId: Any?,
    @Json(name = "exclude_block")
    val excludeBlock: Boolean,
    @Json(name = "exclude_nude")
    val excludeNude: Boolean,
    @Json(name = "exclude_private")
    val excludePrivate: Boolean,
    @Json(name = "include_admin_locks")
    val includeAdminLocks: Boolean,
    @Json(name = "include_comments")
    val includeComments: Boolean,
    @Json(name = "include_deleted")
    val includeDeleted: Boolean,
    @Json(name = "include_equipment_info")
    val includeEquipmentInfo: Boolean,
    @Json(name = "include_follow_info")
    val includeFollowInfo: Boolean,
    @Json(name = "include_geo")
    val includeGeo: Boolean,
    @Json(name = "include_licensing")
    val includeLicensing: Boolean,
    @Json(name = "include_like_by")
    val includeLikeBy: Boolean,
    @Json(name = "include_tags")
    val includeTags: Boolean,
    @Json(name = "include_user_info")
    val includeUserInfo: Boolean,
    @Json(name = "only_user_active")
    val onlyUserActive: Boolean
)