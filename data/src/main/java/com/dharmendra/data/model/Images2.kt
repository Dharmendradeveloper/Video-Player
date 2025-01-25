package com.dharmendra.data.model

import com.google.gson.annotations.SerializedName

data class Images2(
    @SerializedName("jpg") var jpg: Jpg? = Jpg(),
    @SerializedName("webp") var webp: Webp? = Webp(),
)