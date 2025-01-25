package com.dharmendra.data.impl

import com.dharmendra.data.mapper.toDomain
import com.dharmendra.data.network.JikanApiService
import com.dharmendra.domain.jikandata.GetJikanListOfDataRepository
import com.dharmendra.domain.jikandata.JikanData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetJikanListOfDataRepositoryImpl @Inject constructor(val jikanApiService: JikanApiService) :
    GetJikanListOfDataRepository {

    override suspend fun getAnimeList(): List<JikanData> {
        var movieList = arrayListOf<JikanData>()
        //listOf<JikanData>(JikanData("____",90,"Good","htttps://"))
         withContext(Dispatchers.IO) {
             movieList = jikanApiService.getAnimeList().toDomain() as ArrayList<JikanData>
             withContext(Dispatchers.Main){
                 movieList
             }
         }
        return movieList
    }
}