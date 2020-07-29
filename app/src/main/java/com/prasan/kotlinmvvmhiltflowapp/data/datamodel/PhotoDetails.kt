package com.prasan.kotlinmvvmhiltflowapp.data.datamodel

import android.os.Parcel
import android.os.Parcelable

data class PhotoDetails(
    val url: String?,
    val description: String?,
    val votesCount: Int?,
    val comments: Int?,
    val pulse: Double?,
    val impressions: Int?,
    val photoTitle: String?,
    val userName: String?,
    val userPhotoUrl: String?,
    val exif: String?,
    val durationPosted: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Double::class.java.classLoader) as Double?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(url)
        writeString(description)
        writeValue(votesCount)
        writeValue(comments)
        writeValue(pulse)
        writeValue(impressions)
        writeString(photoTitle)
        writeString(userName)
        writeString(userPhotoUrl)
        writeString(exif)
        writeString(durationPosted)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PhotoDetails> = object : Parcelable.Creator<PhotoDetails> {
            override fun createFromParcel(source: Parcel): PhotoDetails = PhotoDetails(source)
            override fun newArray(size: Int): Array<PhotoDetails?> = arrayOfNulls(size)
        }
    }
}