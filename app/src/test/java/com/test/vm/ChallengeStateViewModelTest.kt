package com.test.vm

import app.cash.turbine.test
import com.test.base.BaseTest
import com.test.data.entity.ChallengeList
import com.test.data.entity.UserData
import com.test.domain.usecase.GetChallengesUseCase
import com.test.presentation.base.BaseState
import com.test.presentation.main.fragment.challenge.list.ChallengeListState
import com.test.presentation.main.fragment.challenge.list.ChallengeStateViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ChallengeStateViewModelTest : BaseTest() {

    private val getChallengesUseCase: GetChallengesUseCase = mockk()
    private lateinit var viewModel: ChallengeStateViewModel

    override fun initValues() {
        viewModel = ChallengeStateViewModel(getChallengesUseCase)
    }

    @Test
    fun `check when api returns list for the 1 page`() = runTest {
        every { getChallengesUseCase.getCurrentPage() } returns 1L
        coEvery { getChallengesUseCase.execute(any()) } coAnswers { mockChallenges(isEmpty = false) }
        testJob = launch {
            viewModel.states.test {
                viewModel.getItems(mockString)
                val idle = awaitItem()
                assert(idle is BaseState.Idle)
                val loading = awaitItem()
                assert(loading is BaseState.ShowLoading)
                val result = awaitItem()
                assert(result is ChallengeListState.LoadedItems && result.data.isNotEmpty())
                val hideLoading = awaitItem()
                assert(hideLoading is BaseState.HideLoading)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `check when api returns empty list`() = runTest {
        every { getChallengesUseCase.getCurrentPage() } returns 1L
        coEvery { getChallengesUseCase.execute(any()) } coAnswers { mockChallenges(isEmpty = true) }
        testJob = launch {
            viewModel.states.test {
                viewModel.getItems(mockString)
                val idle = awaitItem()
                assert(idle is BaseState.Idle)
                val loading = awaitItem()
                assert(loading is BaseState.ShowLoading)
                val result = awaitItem()
                assert(result is ChallengeListState.LoadedItems && result.data.isEmpty())
                val hideLoading = awaitItem()
                assert(hideLoading is BaseState.HideLoading)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `check when api returns error`()  = runTest {
        every { getChallengesUseCase.getCurrentPage() } returns 1L
        coEvery { getChallengesUseCase.execute(any()) } coAnswers { mockChallenges(isEmpty = true) }
        testJob = launch {
            viewModel.states.test {
                viewModel.getItems(mockString)
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

    @Test
    fun `check item click with navigation`() = runTest {
        testJob = launch {
            viewModel.states.test {
                viewModel.onItemClicked(mockRandomUserData())
                val idle = awaitItem()
                assert(idle is BaseState.Idle)
                val openItem = awaitItem()
                assert(openItem is ChallengeListState.OpenItem)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    private fun mockChallenges(isEmpty: Boolean): ChallengeList {
        return when (isEmpty) {
            true -> ChallengeList(entities = emptyList(), totalItems = 0, totalPages = 0)
            false -> ChallengeList(
                entities = listOf(mockRandomUserData(), mockRandomUserData(), mockRandomUserData()),
                totalItems = 3,
                totalPages = 1
            )
        }
    }

    private fun mockRandomUserData(): UserData {
        return UserData(
            completedAt = mockString,
            completedLanguages = listOf(mockString, mockString, mockString),
            id = mockString,
            name = mockString,
            slug = mockString
        )
    }
}