package com.test.presentation.base.listener

interface OnSelectionListener<T> {
    fun onItemSelected(item: T, isChecked: Boolean)
    fun onItemsSelected(items: List<T>)
}