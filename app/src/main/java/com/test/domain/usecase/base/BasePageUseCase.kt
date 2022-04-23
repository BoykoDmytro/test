package com.test.domain.usecase.base

abstract class BasePageUseCase<in Parameter, out Result> : BaseUseCase<Parameter, Result>() {

    companion object {
        private const val START_PAGE = 1L
    }

    private var page: Long = START_PAGE

    fun increasePage(): Long = ++page

    fun getCurrentPage(): Long = page

    fun resetPage() {
        page = START_PAGE
    }
}