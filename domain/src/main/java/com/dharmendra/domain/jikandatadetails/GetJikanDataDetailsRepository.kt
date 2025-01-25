package com.dharmendra.domain.jikandatadetails

interface GetJikanDataDetailsRepository {
    suspend fun getAnimeById(animeId: Int): JikanDatadetails
}