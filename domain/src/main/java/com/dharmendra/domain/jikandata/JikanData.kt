package com.dharmendra.domain.jikandata

data class JikanData(
    val title: String,
    val noOfEpisode: Int,
    val rating: String,
    val imgUrl: String,
    val anim_id:Int = 0
)
