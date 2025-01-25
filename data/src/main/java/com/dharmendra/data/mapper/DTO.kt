package com.dharmendra.data.mapper

import com.dharmendra.data.model.Data
import com.dharmendra.data.model.DetailsResponse
import com.dharmendra.data.model.JikanResponse
import com.dharmendra.domain.jikandata.JikanData
import com.dharmendra.domain.jikandatadetails.JikanDatadetails
import retrofit2.Response

fun Response<JikanResponse>.toDomain(): List<JikanData> {
    val jikanData = arrayListOf<JikanData>()
    if (this.isSuccessful) {
        val data = this.body()?.data
        data?.map { item ->
            jikanData.add(
                JikanData(
                    item.title ?: "",
                    item.episodes ?: 0,
                    item.rating ?: "",
                    item.images?.jpg?.imageUrl ?: "",
                    anim_id = item.malId ?: 0
                )
            )
        }
    }
    return jikanData
}

fun Response<DetailsResponse>.toDomainDetails(): JikanDatadetails {
    var jikanDatadetails: JikanDatadetails? = null
    //raw() method prints this==> Body: Response{protocol=http/1.1, code=200, message=OK, url=https://api.jikan.moe/v4/anime/60022}
    if (this.isSuccessful) {
        print("Body: ${this.raw()}")
        val data = this.body()?.data
        print("Jikan Data: $data")
        jikanDatadetails = JikanDatadetails(
            videoUrl = data?.trailer?.youtubeId,
            title = data?.title,
            synopsis = data?.synopsis,
            noOfEpisode = data?.episodes ?: 0,
            rating = data?.rating ?: "",
            imgUrl = data?.trailer?.images?.imageUrl
        )
    }
    return jikanDatadetails!!
}