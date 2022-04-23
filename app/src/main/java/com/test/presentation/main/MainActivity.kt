package com.test.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.test.databinding.ActivityMainBinding
import com.test.presentation.base.BaseActivity
import com.test.presentation.base.BaseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun onCreateViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
    }

    override fun onBindState(state: BaseState) {}
}