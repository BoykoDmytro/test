package com.test.presentation.main

import com.test.presentation.base.BaseState
import com.test.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor() : BaseViewModel<BaseState>(BaseState.Idle)