package com.test.presentation.main.fragment.challenge.detail

import androidx.lifecycle.viewModelScope
import com.test.domain.usecase.GetChallengeByIdUseCase
import com.test.presentation.base.BaseState
import com.test.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class ChallengeDetailsViewModel @Inject constructor(
    private val getChallengeByIdUseCase: GetChallengeByIdUseCase
) : BaseViewModel<BaseState>(BaseState.Idle) {

    lateinit var userName: String

    fun getChallengeById(challengeId: String) {
        viewModelScope.launch {
            state = BaseState.ShowLoading
            try {
                val challenge = withContext(Dispatchers.IO) {
                    getChallengeByIdUseCase.execute(challengeId)
                }
                state = ChallengeDetailsState.InitChallenge(challenge, userName)
            } finally {
                state = BaseState.HideLoading
            }
        }

    }
}