package com.arash.altafi.websocket5.model

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("image")
    val image: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("title")
    val title: String
)