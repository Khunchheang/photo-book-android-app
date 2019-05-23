package com.khunchheang.photobook.restclient

import com.khunchheang.photobook.data.PhotoItemResponse
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