package com.test.data.entity

data class UserData(
    val completedAt: String,
    val completedLanguages: List<String>?,
    val id: String,
    val name: String,
    val slug: String
)