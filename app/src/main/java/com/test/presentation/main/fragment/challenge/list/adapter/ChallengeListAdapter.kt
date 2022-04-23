package com.test.presentation.main.fragment.challenge.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.test.data.entity.UserData
import com.test.databinding.LiChallengeItemBinding
import com.test.presentation.base.adapter.BaseAdapter
import com.test.presentation.base.listener.OnItemClickListener
import com.test.presentation.main.fragment.challenge.list.adapter.viewholder.ChallengeListVH

class ChallengeListAdapter(
    data: MutableList<UserData> = mutableListOf(),
    private val onItemClickListener: OnItemClickListener<UserData>
) :
    BaseAdapter<UserData, ChallengeListVH>(data) {

    init {
        setHasStableIds(true)
        selectedType = SelectedType.None
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListVH {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = LiChallengeItemBinding.inflate(inflater, parent, false)
        return ChallengeListVH(viewBinding, onItemClickListener)
    }

    override fun getItemId(position: Int): Long =
        getItem(position)?.id?.hashCode()?.toLong() ?: super.getItemId(position)
}