package com.test.data.entity

data class Challenge(
    val approvedAt: String,
    val approvedBy: ApprovedBy,
    val category: String,
    val createdBy: CreatedBy,
    val description: String,
    val id: String,
    val languages: List<String>,
    val name: String,
    val publishedAt: String,
    val rank: Rank,
    val slug: String,
    val tags: List<String>,
    val totalAttempts: Int,
    val totalCompleted: Int,
    val totalStars: Int,
    val url: String,
    val voteScore: Int
)