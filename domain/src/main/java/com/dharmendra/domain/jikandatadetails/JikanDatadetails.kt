package com.dharmendra.domain.jikandatadetails

data class JikanDatadetails(
    val videoUrl: String? = null,
    val title: String? = null,
    val synopsis: String? = null,
    val mainCast: String? = null,
    val noOfEpisode: Int = 0,
    val rating: String = "0.0",
    val imgUrl:String? = null
)