package com.dharmendra.data.network

import com.dharmendra.data.model.Data
import com.dharmendra.data.model.DetailsResponse
import com.dharmendra.data.model.JikanResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApiService {

    @GET("/v4/top/anime")
    suspend fun getAnimeList(): Response<JikanResponse>

    @GET("/v4/anime/{id}")
    suspend fun getAnimeById(@Path("id") animeId: Int): Response<DetailsResponse>
}