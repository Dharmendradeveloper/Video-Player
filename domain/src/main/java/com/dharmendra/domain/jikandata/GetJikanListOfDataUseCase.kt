package com.dharmendra.domain.jikandata

import javax.inject.Inject

class GetJikanListOfDataUseCase @Inject constructor(val getJikanListOfDataRepository: GetJikanListOfDataRepository) {
    suspend fun getAnimeList(): List<JikanData> {
        return getJikanListOfDataRepository.getAnimeList()
    }
}