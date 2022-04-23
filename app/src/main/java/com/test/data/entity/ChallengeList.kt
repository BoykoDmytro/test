package com.test.data.entity

import com.google.gson.annotations.SerializedName

data class ChallengeList(
    @SerializedName("data")
    val entities: List<UserData>,
    val totalItems: Int,
    val totalPages: Int
)