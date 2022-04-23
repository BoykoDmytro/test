package com.test.domain.usecase

import com.test.data.entity.ChallengeList
import com.test.domain.repository.IChallengeRepository
import com.test.domain.usecase.base.BasePageUseCase
import javax.inject.Inject

class GetChallengeUseCase @Inject constructor(private val iChallengeRepository: IChallengeRepository) :
    BasePageUseCase<String, ChallengeList>() {

    override suspend fun execute(params: String): ChallengeList =
        iChallengeRepository.getChallenges(params, getCurrentPage())
}