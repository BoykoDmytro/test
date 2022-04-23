package com.test.presentation.main.fragment.challenge.list.adapter.viewholder

import android.content.Context
import com.google.android.material.chip.Chip
import com.test.common.DateUtils
import com.test.data.entity.UserData
import com.test.databinding.LiChallengeItemBinding
import com.test.presentation.base.adapter.viewholder.BaseViewHolder
import com.test.presentation.base.listener.OnItemClickListener

class ChallengeListVH(
    private val viewBinding: LiChallengeItemBinding,
    private val onItemClickListener: OnItemClickListener<UserData>? = null
) : BaseViewHolder<UserData>(viewBinding) {

    override fun bind(data: UserData, isSelected: Boolean) {
        val context = itemView.context
        isCheckedItem = isSelected
        with(viewBinding) {
            liChallengeItemName.text = data.name
            liChallengeItemTime.text = DateUtils.toDateFormat(data.completedAt)
            data.completedLanguages?.forEach {
                liChallengeItemLanguages.addView(context.createChipItem(it))
            }
            root.setOnClickListener {
                onItemClickListener?.onItemClick(data)
            }
        }
    }

    private fun Context.createChipItem(name: String): Chip = Chip(this).apply {
        text = name
        isChipIconVisible = false
        isCloseIconVisible = false
        isClickable = false
        isCheckable = false
    }
}