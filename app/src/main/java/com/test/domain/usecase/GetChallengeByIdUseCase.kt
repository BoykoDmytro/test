package com.test.domain.usecase

import com.test.data.entity.Challenge
import com.test.domain.repository.IChallengeRepository
import com.test.domain.usecase.base.BaseUseCase
import javax.inject.Inject

class GetChallengeByIdUseCase @Inject constructor(private val iChallengeRepository: IChallengeRepository) :
    BaseUseCase<String, Challenge>() {

    override suspend fun execute(params: String): Challenge =
        iChallengeRepository.getChallengeById(params)
}