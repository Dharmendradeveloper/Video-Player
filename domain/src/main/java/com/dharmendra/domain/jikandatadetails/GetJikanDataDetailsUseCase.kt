package com.dharmendra.domain.jikandatadetails

import javax.inject.Inject

class GetJikanDataDetailsUseCase @Inject constructor(val getJikanDataDetailsRepository: GetJikanDataDetailsRepository) {

    suspend fun getAnimeById(animeId: Int): JikanDatadetails{
        return getJikanDataDetailsRepository.getAnimeById(animeId = animeId)
    }
}