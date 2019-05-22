package com.khunchheang.photobookmark.restclient

import com.khunchheang.photobookmark.data.PhotoItemResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PicSumRestApi {

    @GET("v2/list")
    fun getPhotoList(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<List<PhotoItemResponse>>

}