package com.test.presentation.base.listener

import com.test.presentation.base.adapter.viewholder.BaseViewHolder


interface OnCheckBoxSelectionListener<T> {
    fun selectionClick(vh: BaseViewHolder<T>, checked: Boolean)
}