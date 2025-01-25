package com.dharmendra.domain.jikandata

interface GetJikanListOfDataRepository {
    suspend fun getAnimeList(): List<JikanData>
}