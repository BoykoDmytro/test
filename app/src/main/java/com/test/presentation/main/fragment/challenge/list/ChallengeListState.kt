package com.test.presentation.main.fragment.challenge.list

import com.test.data.entity.UserData
import com.test.presentation.base.BaseState
import com.test.presentation.main.MainState

internal interface ChallengeListState : BaseState{
    data class LoadedItems(val data: MutableList<UserData>) : ChallengeListState
    data class OpenItem(val userData: UserData) : ChallengeListState
}