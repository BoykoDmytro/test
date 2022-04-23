package com.test.presentation.main.fragment.challenge.list

import androidx.lifecycle.viewModelScope
import com.test.data.entity.UserData
import com.test.domain.usecase.GetChallengeUseCase
import com.test.presentation.base.BaseState
import com.test.presentation.base.BaseViewModel
import com.test.presentation.base.listener.PaginationListener
import com.test.presentation.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class ChallengeStateViewModel @Inject constructor(
    private val getChallengeUseCase: GetChallengeUseCase
) : BaseViewModel<BaseState>(BaseState.Idle), PaginationListener.OnLoadMoreListener {

    private var hasMoreItems = true
    private var job: Job? = null
    private var totalPages = 0

    fun getItems(user: String) {
        job = viewModelScope.launch {
            state = BaseState.ShowLoading
            try {
                val data = withContext(Dispatchers.IO) { getChallengeUseCase.execute(user) }
                hasMoreItems = totalPages > getChallengeUseCase.getCurrentPage()
                state = ChallengeListState.LoadedItems(data.entities.toMutableList())
            } catch (e: Exception) {
                state = BaseState.Error(e)
            } finally {
                state = BaseState.HideLoading
            }
        }
    }

    override fun loadMoreItems() {
        getChallengeUseCase.increasePage()
        viewModelScope.launch { job }
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