package com.example.moviestesttask.data.network.model

import com.google.gson.annotations.SerializedName

data class RequestUserTokenModel(
    @field:SerializedName("success")
    var success: Boolean? = null,

    @field:SerializedName("expires_at")
    var expiresAt: String? = null,

    @field:SerializedName("request_token")
    var requestToken: String? = null
)
