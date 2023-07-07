package com.plz.no.anr.lol.domain.usecase.key

import com.plz.no.anr.lol.domain.extentions.asFlow
import com.plz.no.anr.lol.domain.repository.AppRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetKeyUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: AppRepository
) : BaseUseCase<Unit, String?>(coroutineDispatcher) {
    override fun execute(parameter: Unit): Flow<Result<String?>> {
        return Result.success(repository.apiKey).asFlow()
    }

}