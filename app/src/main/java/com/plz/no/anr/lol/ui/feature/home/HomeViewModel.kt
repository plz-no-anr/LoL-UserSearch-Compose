package com.plz.no.anr.lol.ui.feature.home

import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol.domain.model.Profile
import com.plz.no.anr.lol.domain.usecase.key.DeleteKeyUseCase
import com.plz.no.anr.lol.domain.usecase.key.GetKeyUseCase
import com.plz.no.anr.lol.domain.usecase.key.InsertKeyUseCase
import com.plz.no.anr.lol.domain.usecase.profile.GetProfileUseCase
import com.plz.no.anr.lol.domain.usecase.profile.InsertProfileUseCase
import com.plz.no.anr.lol.domain.usecase.summoner.DeleteAllSummonerUseCase
import com.plz.no.anr.lol.domain.usecase.summoner.DeleteSummonerUseCase
import com.plz.no.anr.lol.domain.usecase.summoner.GetSummonerListUseCase
import com.plz.no.anr.lol.domain.usecase.summoner.RefreshSummonerListUseCase
import com.plz.no.anr.lol.ui.feature.home.HomeContract.Intent
import com.plz.no.anr.lol.ui.feature.home.HomeContract.SideEffect
import com.plz.no.anr.lol.ui.feature.home.HomeContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import plznoanr.coma.core.ComaViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deleteAllSummonerUseCase: DeleteAllSummonerUseCase,
    private val deleteSummonerUseCase: DeleteSummonerUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val insertProfileUseCase: InsertProfileUseCase,
    private val getKeyUseCase: GetKeyUseCase,
    private val insertKeyUseCase: InsertKeyUseCase,
    private val deleteKeyUseCase: DeleteKeyUseCase,
    private val getSummonerListUseCase: GetSummonerListUseCase,
    private val refreshSummonerListUseCase: RefreshSummonerListUseCase
) : ComaViewModel<State, Intent, SideEffect>() {

    override fun setInitialState(): State = State()

    override fun handleIntents(intent: Intent) {
        when (intent) {
            is Intent.OnLoad -> onLoad()
            is Intent.OnSearch -> postSideEffect { SideEffect.Navigation.ToSearch }
            is Intent.Refresh -> refreshSummonerList()
            is Intent.Summoner.OnDeleteAll -> deleteAllSummoner()
            is Intent.Summoner.OnDelete -> deleteSummoner(intent.name)
            is Intent.Profile.OnAdd -> addProfile(intent.profile)
            is Intent.Key.OnGet -> postSideEffect { SideEffect.MoveGetApiKey }
            is Intent.Key.OnAdd -> insertKey(intent.key)
            is Intent.Key.OnDelete -> deleteKey()
            is Intent.Spectator.OnWatch -> postSideEffect { SideEffect.Navigation.ToSpectator(intent.name) }
        }
    }

    private fun onLoad() {
        combine(
            getKeyUseCase(Unit),
            getProfileUseCase(Unit),
            getSummonerListUseCase(Unit)
        ) { key, profile, summoners ->
            State(
                key = key.getOrNull(),
                profile = profile.getOrNull(),
                data = summoners.getOrElse { emptyList() }
            )
        }.onStart { reduce { copy(isLoading = true) } }
            .onEach {
                reduce {
                    copy(
                        isLoading = false,
                        key = it.key,
                        profile = it.profile,
                        data = it.data?.asReversed(),
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun refreshSummonerList() {
        refreshSummonerListUseCase(Unit)
            .onStart { reduce { copy(isRefreshing = true) } }
            .onEach { result ->
                result.onSuccess {
                    reduce {
                        copy(
                            data = it.asReversed(),
                            isRefreshing = false
                        )
                    }
                }.onFailure {
                    reduce {
                        copy(
                            isRefreshing = false
                        )
                    }
                    postSideEffect {
                        SideEffect.Toast(it.message ?: "Error")
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun deleteAllSummoner() {
        deleteAllSummonerUseCase(Unit)
            .onStart { reduce { copy(isLoading = true) } }
            .onEach { result ->
                result.onSuccess {
                    reduce {
                        copy(
                            data = emptyList(),
                            isLoading = false
                        )
                    }
                }.onFailure {
                    reduce {
                        copy(
                            error = it.message,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun deleteSummoner(name: String) {
        deleteSummonerUseCase(name)
            .onStart { reduce { copy(isLoading = true) } }
            .onEach { result ->
                result.onSuccess {
                    reduce {
                        copy(
                            data = data?.filter { it.name != name }?.asReversed(),
                            isLoading = false
                        )
                    }
                }.onFailure {
                    reduce {
                        copy(
                            error = it.message,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun addProfile(profile: Profile) {
        insertProfileUseCase(profile)
            .onStart { reduce { copy(isLoading = true) } }
            .onEach { result ->
                result.onSuccess {
                    reduce {
                        copy(
                            profile = profile,
                            isLoading = false
                        )
                    }
                }.onFailure {
                    reduce {
                        copy(
                            error = it.message,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun insertKey(key: String) {
        insertKeyUseCase(key)
            .onStart { reduce { copy(isLoading = true) } }
            .onEach { result ->
                result.onSuccess {
                    reduce {
                        copy(
                            key = key,
                            isLoading = false
                        )
                    }
                }.onFailure {
                    reduce {
                        copy(
                            error = it.message,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun deleteKey() {
        deleteKeyUseCase(Unit)
            .onStart { reduce { copy(isLoading = true) } }
            .onEach { result ->
                result.onSuccess {
                    reduce {
                        copy(
                            key = null,
                            isLoading = false
                        )
                    }
                }.onFailure {
                    reduce {
                        copy(
                            error = it.message,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

}