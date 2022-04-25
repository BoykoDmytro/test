package com.test.vm

import app.cash.turbine.test
import com.test.base.BaseTest
import com.test.data.entity.ApprovedBy
import com.test.data.entity.Challenge
import com.test.data.entity.CreatedBy
import com.test.data.entity.Rank
import com.test.domain.usecase.GetChallengeByIdUseCase
import com.test.presentation.base.BaseState
import com.test.presentation.main.fragment.challenge.detail.ChallengeDetailsState
import com.test.presentation.main.fragment.challenge.detail.ChallengeDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ChallengeDetailViewModelTest : BaseTest() {

    private val getChallengeByIdUseCase: GetChallengeByIdUseCase = mockk()
    private lateinit var viewModel: ChallengeDetailsViewModel

    override fun initValues() {
        viewModel = ChallengeDetailsViewModel(getChallengeByIdUseCase)
    }

    @Test
    fun getItemTest() = runTest {
        coEvery { getChallengeByIdUseCase.execute(any()) } coAnswers { mockRandomChallenge() }
        testJob = launch {
            viewModel.states.test {
                viewModel.getChallengeById(mockString)
                val idle = awaitItem()
                assert(idle is BaseState.Idle)
                val loading = awaitItem()
                assert(loading is BaseState.ShowLoading)
                val result = awaitItem()
                assert(result is ChallengeDetailsState.InitChallenge)
                val hideLoading = awaitItem()
                assert(hideLoading is BaseState.HideLoading)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun getItemErrorTest() = runTest {
        coEvery { getChallengeByIdUseCase.execute(any()) } coAnswers { throw IllegalArgumentException() }
        testJob = launch {
            viewModel.states.test {
                viewModel.getChallengeById(mockString)
                val idle = awaitItem()
                assert(idle is BaseState.Idle)
                val loading = awaitItem()
                assert(loading is BaseState.ShowLoading)
                val result = awaitItem()
                assert(result is BaseState.Error)
                val hideLoading = awaitItem()
                assert(hideLoading is BaseState.HideLoading)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    private fun mockRandomChallenge(): Challenge {
        return Challenge(
            approvedAt = mockString,
            approvedBy = ApprovedBy(mockString, mockString),
            category = mockString,
            createdBy = CreatedBy(mockString, mockString),
            description = mockString,
            id = mockString,
            languages = listOf(mockString),
            name = mockString,
            publishedAt = mockString,
            rank = Rank(mockString, mockInt, mockString),
            slug = mockString,
            tags = listOf(mockString),
            totalAttempts = mockInt,
            totalCompleted = mockInt,
            totalStars = mockInt,
            url = mockString,
            voteScore = mockInt
        )
    }
}