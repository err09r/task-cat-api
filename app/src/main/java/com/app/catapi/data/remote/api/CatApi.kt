package com.app.catapi.data.remote.api

import com.app.catapi.data.remote.dto.CatDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("/v1/images/search")
    suspend fun loadCatPage(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<CatDto>>
}