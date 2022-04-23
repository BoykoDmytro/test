package com.test.data.repository

import com.test.data.entity.ChallengeList
import com.test.data.network.service.ChallengeService
import com.test.domain.repository.IChallengeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChallengeRepositoryImpl @Inject constructor(private val service: ChallengeService) :
    IChallengeRepository {

    override suspend fun getChallenges(user: String, page: Long): ChallengeList =
        service.getChallenges(user, page)
}