package com.test.presentation.main.fragment.challenge.list

import androidx.lifecycle.viewModelScope
import com.test.data.entity.UserData
import com.test.domain.usecase.GetChallengesUseCase
import com.test.presentation.base.BaseState
import com.test.presentation.base.BaseViewModel
import com.test.presentation.base.listener.PaginationListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class ChallengeStateViewModel @Inject constructor(
    private val getChallengeUseCase: GetChallengesUseCase
) : BaseViewModel<BaseState>(BaseState.Idle), PaginationListener.OnLoadMoreListener {

    private var hasMoreItems = true
    private var job: Job? = null
    private lateinit var userName: String

    fun getItems(userName: String) {
        this.userName = userName
        viewModelScope.launch { getChallenges(userName, true) }
    }

    private suspend fun getChallenges(userName: String, isFirstLoad: Boolean) {
        try {
            if (isFirstLoad) state = BaseState.ShowLoading
            val data = withContext(Dispatchers.IO) { getChallengeUseCase.execute(userName) }
            hasMoreItems = data.totalPages > getChallengeUseCase.getCurrentPage()
            state = ChallengeListState.LoadedItems(data.entities.toMutableList())
        } catch (e: Exception) {
            state = BaseState.Error(e)
        } finally {
            if (isFirstLoad) state = BaseState.HideLoading
        }
    }

    override fun loadMoreItems() {
        getChallengeUseCase.increasePage()
        job = viewModelScope.launch { getChallenges(userName, false) }
    }

    override fun isLoading(): Boolean = job?.isActive ?: false

    override fun hasMoreItems(): Boolean = hasMoreItems

    override fun onCleared() {
        super.onCleared()
        job = null
    }

    fun onItemClicked(item: UserData) {
        state = ChallengeListState.OpenItem(item)
    }
}