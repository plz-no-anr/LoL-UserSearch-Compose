package com.plz.no.anr.lol.domain.usecase.search

import com.plz.no.anr.lol.domain.model.Search
import com.plz.no.anr.lol.domain.repository.SearchRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GetSearchListUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val searchRepository: SearchRepository
) : BaseUseCase<Unit, List<Search>>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<List<Search>>> {
        return searchRepository.getSearchList().map { result ->
            Result.success(
                result.getOrThrow().sortedBy { it.date.toLong() }
                    .map {
                        it.copy(
                            date = SimpleDateFormat(
                                "yyyy-MM-dd kk:mm:ss",
                                Locale("ko", "KR")
                            ).format(Date(it.date.toLong()))
                        )
                    }
            )
        }
    }

}
