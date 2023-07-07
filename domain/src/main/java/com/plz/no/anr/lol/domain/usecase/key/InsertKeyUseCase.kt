package com.plz.no.anr.lol.domain.usecase.key

import com.plz.no.anr.lol.domain.extentions.asFlow
import com.plz.no.anr.lol.domain.repository.AppRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class InsertKeyUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: AppRepository
): BaseUseCase<String, Unit>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Unit>> {
        repository.apiKey = parameter
        return Result.success(Unit).asFlow()
    }

}