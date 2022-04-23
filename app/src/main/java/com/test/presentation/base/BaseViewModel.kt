package com.test.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<S : BaseState>(defaultState: S) : ViewModel() {

    // STATES
    private val mutableStates = MutableStateFlow(defaultState)
    val states = mutableStates.asStateFlow()
    protected var state: S
        get() = mutableStates.value
        set(value) {
            mutableStates.value = value
        }
}