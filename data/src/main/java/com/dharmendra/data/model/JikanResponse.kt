package com.dharmendra.data.model

import com.google.gson.annotations.SerializedName


data class JikanResponse(
    @SerializedName("pagination") var pagination: Pagination? = Pagination(),
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf()
)