package com.plznoanr.lol.core.domain.usecase.search

import com.plznoanr.lol.core.data.repository.SearchRepository
import javax.inject.Inject

class DeleteSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(name: String) {
        return searchRepository.deleteSearch(name)
    }

}