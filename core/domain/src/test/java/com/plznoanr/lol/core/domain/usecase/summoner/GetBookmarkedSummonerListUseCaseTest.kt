package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.model.getDummySummoner
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.single

internal class GetBookmarkedSummonerListUseCaseTest : FunSpec() {

    private val repository = FakeSummonerRepository(
        summoners = generateSummonerList(size = 10),
        bookmarkedSummonerIds = bookmarkedIds
    )

    private val useCase = GetBookmarkedSummonerListUseCase(
        getSummonerListUseCase = GetSummonerListUseCase(
            summonerRepository = repository
        )
    )

    init {
        context("") {
            test("북마크된 소환사가 없을 경우 빈 목록을 반환한다") {
                val summoners = useCase().single()

                summoners.size shouldBe 0
            }
        }
        test("북마크된 소환사 목록을 반환한다") {
            val summoners = useCase().single()

            summoners.size shouldBe bookmarkedIds.size
            summoners.all { it.isBookMarked } shouldBe true
        }
    }


    companion object {
        private val bookmarkedIds = setOf("Summoner Id-1", "Summoner Id-3", "Summoner Id-5")
    }

    private fun generateSummonerList(size: Int) = (0..size).mapIndexed { index, i ->
        getDummySummoner(index = index)
    }


}
