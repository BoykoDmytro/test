package com.test.presentation.base

interface BaseState {
    object Idle : BaseState
    object ShowLoading : BaseState
    object HideLoading : BaseState
    class Error(val error: Throwable) : BaseState
}