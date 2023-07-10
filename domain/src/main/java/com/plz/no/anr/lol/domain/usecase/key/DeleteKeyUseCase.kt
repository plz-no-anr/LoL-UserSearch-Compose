package com.plz.no.anr.lol.domain.usecase.key

import com.plz.no.anr.lol.domain.repository.AppRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class DeleteKeyUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: AppRepository
) : BaseUseCase<Unit, Unit>(coroutineDispatcher) {
    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return repository.deleteApiKey()
    }

}