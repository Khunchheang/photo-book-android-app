package com.khunchheang.photobook.data

import com.google.gson.annotations.SerializedName

class PhotoItemResponse {

    @field:SerializedName("author")
    val author: String? = null

    @field:SerializedName("width")
    val width: Int? = null

    @field:SerializedName("download_url")
    val downloadUrl: String? = null

    @field:SerializedName("id")
    val id: String? = null

    @field:SerializedName("url")
    val url: String? = null

    @field:SerializedName("height")
    val height: Int? = null

    val listUrl: String
        get() = "https://picsum.photos/id/$id/500/300"

    var isAddedBookmark: Boolean = false
}