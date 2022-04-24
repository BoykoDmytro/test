package com.test.presentation.main.fragment.challenge.detail

import com.test.data.entity.Challenge
import com.test.presentation.base.BaseState

internal interface ChallengeDetailsState : BaseState {
    data class InitChallenge(val challenge: Challenge, val userName: String) : ChallengeDetailsState
}