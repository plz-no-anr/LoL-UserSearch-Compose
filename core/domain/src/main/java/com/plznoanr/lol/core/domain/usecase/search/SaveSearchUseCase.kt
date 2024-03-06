package com.plznoanr.lol.core.domain.usecase.search

import com.plznoanr.lol.core.data.repository.SearchRepository
import com.plznoanr.lol.core.model.Nickname
import com.plznoanr.lol.core.model.Search
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class SaveSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(nickname: Nickname) {
        searchRepository.upsertSearch(
            Search(
                nickname = nickname,
                date = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            )
        )
    }
}