package com.test.domain.usecase.base

abstract class BaseUseCase<in Parameter, out Result> {

    abstract suspend fun execute(params: Parameter): Result
}