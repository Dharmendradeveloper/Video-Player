package com.dharmendra.data.model

import com.google.gson.annotations.SerializedName

data class DetailsResponse(
    @SerializedName("data" ) var data : Data? = Data()
)
