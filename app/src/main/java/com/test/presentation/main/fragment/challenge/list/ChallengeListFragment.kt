package com.test.presentation.main.fragment.challenge.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.test.databinding.FragmentChallengeBinding
import com.test.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ChallengeListFragment :
    BaseFragment<FragmentChallengeBinding, ChallengeStateViewModel>() {

    override val viewModel: ChallengeStateViewModel by viewModels()

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChallengeBinding = FragmentChallengeBinding.inflate(inflater, container, false)
}