package com.plz.no.anr.lol_usersearch_compose.ui.feature.main

import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.usecase.key.DeleteKeyUseCase
import com.plznoanr.domain.usecase.key.GetKeyUseCase
import com.plznoanr.domain.usecase.key.InsertKeyUseCase
import com.plznoanr.domain.usecase.profile.GetProfileUseCase
import com.plznoanr.domain.usecase.profile.InsertProfileUseCase
import com.plznoanr.domain.usecase.summoner.DeleteAllSummonerUseCase
import com.plznoanr.domain.usecase.summoner.DeleteSummonerUseCase
import com.plznoanr.domain.usecase.summoner.ReadSummonerListUseCase
import com.plznoanr.domain.usecase.summoner.RefreshSummonerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteAllSummonerUseCase: DeleteAllSummonerUseCase,
    private val deleteSummonerUseCase: DeleteSummonerUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val insertProfileUseCase: InsertProfileUseCase,
    private val getKeyUseCase: GetKeyUseCase,
    private val insertKeyUseCase: InsertKeyUseCase,
    private val deleteKeyUseCase: DeleteKeyUseCase,
    private val readSummonerListUseCase: ReadSummonerListUseCase,
    private val refreshSummonerListUseCase: RefreshSummonerListUseCase
) : BaseViewModel<MainContract.UiState, MainContract.Event, MainContract.Effect>() {

    override fun setInitialState(): MainContract.UiState = MainContract.UiState(
        data = emptyList()
    )

    override fun handleEvents(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnLoad -> onLoad()
            is MainContract.Event.OnSearch -> setEffect { MainContract.Effect.Navigation.ToSearch }
            is MainContract.Event.Refresh -> refreshSummonerList()
            is MainContract.Event.Summoner.OnDeleteAll -> deleteAllSummoner()
            is MainContract.Event.Summoner.OnDelete -> deleteSummoner(event.name)
            is MainContract.Event.Profile.OnAdd -> addProfile(event.profile)
            is MainContract.Event.Key.OnAdd -> insertKey(event.key)
            is MainContract.Event.Key.OnDelete -> deleteKey()
            is MainContract.Event.Spectator.OnWatch -> setEffect {
                MainContract.Effect.Navigation.ToSpectator(
                    event.name
                )
            }
        }
    }

    private fun onLoad() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                combine(
                    getKeyUseCase(Unit),
                    getProfileUseCase(Unit),
                    readSummonerListUseCase(Unit)
                ) { k, p, r ->
                    if (k.isSuccess && p.isSuccess && r.isSuccess) {
                        k.getOrNull() to p.getOrNull() to r.getOrElse { emptyList() }
                    } else {
                        null to null to emptyList()
                    }
                }.onStart { setState { copy(isLoading = true) } }
                    .collect {
                        setState {
                            copy(
                                key = it.first.first,
                                profile = it.first.second,
                                data = it.second.asReversed(),
                                isLoading = false
                            )
                        }
                    }
            }
        }
    }

    private fun refreshSummonerList() {
        viewModelScope.launch {
            refreshSummonerListUseCase(Unit)
                .onStart { setState { copy(isRefreshing = true) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                data = it.asReversed(),
                                isRefreshing = false
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                isRefreshing = false
                            )
                        }
                        setEffect {
                            MainContract.Effect.Toast(it.message ?: "Error")
                        }
                    }
                }
        }
    }

    private fun deleteAllSummoner() {
        viewModelScope.launch {
            deleteAllSummonerUseCase(Unit)
                .onStart { setState { copy(isLoading = true) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                data = emptyList(),
                                isLoading = false
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                error = it.message,
                                isLoading = false
                            )
                        }
                    }

                }
        }
    }

    private fun deleteSummoner(name: String) {
        viewModelScope.launch {
            deleteSummonerUseCase(name)
                .onStart { setState { copy(isLoading = true) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                data = data.filter { it.name != name }.asReversed(),
                                isLoading = false
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                error = it.message,
                                isLoading = false
                            )
                        }
                    }

                }
        }
    }

    private fun addProfile(profile: Profile) {
        viewModelScope.launch {
            insertProfileUseCase(profile)
                .onStart { setState { copy(isLoading = true) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                profile = profile,
                                isLoading = false
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                error = it.message,
                                isLoading = false
                            )
                        }
                    }

                }
        }
    }

    private fun insertKey(key: String) {
        viewModelScope.launch {
            insertKeyUseCase(key)
                .onStart { setState { copy(isLoading = true) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                key = key,
                                isLoading = false
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                error = it.message,
                                isLoading = false
                            )
                        }
                    }

                }
        }
    }

    private fun deleteKey() {
        viewModelScope.launch {
            deleteKeyUseCase(Unit)
                .onStart { setState { copy(isLoading = true) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                key = null,
                                isLoading = false
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                error = it.message,
                                isLoading = false
                            )
                        }
                    }

                }
        }
    }

}