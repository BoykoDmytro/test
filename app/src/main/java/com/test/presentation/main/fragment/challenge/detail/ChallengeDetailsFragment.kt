package com.test.presentation.main.fragment.challenge.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.R
import com.test.databinding.FragmentChallengeDetailsBinding
import com.test.presentation.base.BaseFragment
import com.test.presentation.base.BaseState
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
internal class ChallengeDetailsFragment :
    BaseFragment<FragmentChallengeDetailsBinding, ChallengeDetailsViewModel>() {

    override val viewModel: ChallengeDetailsViewModel by viewModels()

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChallengeDetailsBinding =
        FragmentChallengeDetailsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val challengeId = ChallengeDetailsFragmentArgs.fromBundle(requireArguments()).challengeId
        val userName = ChallengeDetailsFragmentArgs.fromBundle(requireArguments()).userName
        viewModel.userName = userName
        viewModel.getChallengeById(challengeId)
    }

    override fun onBindState(state: BaseState) {
        when (state) {
            is ChallengeDetailsState.InitChallenge -> handleInitState(state)
            else -> super.onBindState(state)
        }
    }

    override fun FragmentChallengeDetailsBinding.onInitializeViews() {
        fChallengeDetailsToolbar.setNavigationOnClickListener { onBackPressed() }
        backButtonAction { onBackPressed() }
    }

    private fun onBackPressed() {
        findNavController().popBackStack()
    }

    private fun handleInitState(state: ChallengeDetailsState.InitChallenge) {
        with(binding) {
            val challenge = state.challenge
            val userName = state.userName
            val title = String.format(
                Locale.ROOT,
                getString(R.string.challenge_name_title),
                userName,
                challenge.name
            )
            fChallengeDetailsToolbar.title = title
            fChallengeDetailsName.setValueText(challenge.name)
            fChallengeDetailsUrl.setValueText(challenge.url)
            fChallengeDetailsDescription.setValueText(challenge.description)
            fChallengeDetailsTotalAttempts.setValueText(challenge.totalAttempts.toString())
            fChallengeDetailsTotalCompleted.setValueText(challenge.totalCompleted.toString())
            fChallengeDetailsTotalStars.setValueText(challenge.totalStars.toString())
        }
    }
}