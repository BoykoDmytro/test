package com.test.presentation.main.fragment.challenge.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.R
import com.test.common.toDp
import com.test.data.entity.UserData
import com.test.databinding.FragmentChallengeListBinding
import com.test.presentation.base.BaseFragment
import com.test.presentation.base.BaseState
import com.test.presentation.base.adapter.decorator.SpacesItemDecoration
import com.test.presentation.base.listener.OnItemClickListener
import com.test.presentation.base.listener.PaginationListener
import com.test.presentation.main.fragment.challenge.list.adapter.ChallengeListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
internal class ChallengeListFragment :
    BaseFragment<FragmentChallengeListBinding, ChallengeStateViewModel>(),
    OnItemClickListener<UserData> {

    companion object {
        private const val USER_NAME = "Voile"
    }

    private val challengeListAdapter by lazy { ChallengeListAdapter(onItemClickListener = this) }

    override val viewModel: ChallengeStateViewModel by viewModels()

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChallengeListBinding =
        FragmentChallengeListBinding.inflate(inflater, container, false)

    override fun onBindState(state: BaseState) {
        when (state) {
            is ChallengeListState.LoadedItems -> handleItems(state.data)
            is ChallengeListState.OpenItem -> handleOpenItem(state.userData)
            else -> super.onBindState(state)
        }
    }

    private fun handleOpenItem(userData: UserData) {
        val challengeId = userData.id
        val direction = ChallengeListFragmentDirections.actionChallengeListToChallengeDetails(
            challengeId,
            USER_NAME
        )
        findNavController().navigate(direction)
    }

    private fun handleItems(data: MutableList<UserData>) {
        challengeListAdapter.addItems(data)
    }

    override fun FragmentChallengeListBinding.onInitializeViews() {
        fChallengeListToolbar.title =
            String.format(Locale.ROOT, getString(R.string.challenge_list), USER_NAME)
        with(fChallengeListRv) {
            adapter = challengeListAdapter
            val llm = LinearLayoutManager(requireContext())
            layoutManager = llm
            addItemDecoration(SpacesItemDecoration(16.toDp(), 8.toDp()))
            addOnScrollListener(PaginationListener(viewModel))
            addItemDecoration(DividerItemDecoration(requireContext(), llm.orientation))
        }
        viewModel.getItems(USER_NAME)
    }

    override fun onItemClick(item: UserData) {
        viewModel.onItemClicked(item)
    }
}