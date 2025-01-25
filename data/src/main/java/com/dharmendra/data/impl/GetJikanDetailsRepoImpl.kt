package com.dharmendra.data.impl

import com.dharmendra.data.mapper.toDomainDetails
import com.dharmendra.data.network.JikanApiService
import com.dharmendra.domain.jikandatadetails.GetJikanDataDetailsRepository
import com.dharmendra.domain.jikandatadetails.JikanDatadetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetJikanDetailsRepoImpl @Inject constructor(private val remoteApiService: JikanApiService):GetJikanDataDetailsRepository {
    override suspend fun getAnimeById(animeId: Int): JikanDatadetails {
        return withContext(Dispatchers.IO){
            remoteApiService.getAnimeById(animeId = animeId).toDomainDetails()
        }
    }

}