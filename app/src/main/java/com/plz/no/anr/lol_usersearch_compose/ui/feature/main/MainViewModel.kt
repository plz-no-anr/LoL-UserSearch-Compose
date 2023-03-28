package com.plz.no.anr.lol_usersearch_compose.ui.feature.main

import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.usecase.key.DeleteKeyUseCase
import com.plznoanr.domain.usecase.key.GetKeyUseCase
import com.plznoanr.domain.usecase.key.InsertKeyUseCase
import com.plznoanr.domain.usecase.profile.GetProfileUseCase
import com.plznoanr.domain.usecase.profile.InsertProfileUseCase
import com.plznoanr.domain.usecase.summoner.DeleteAllSummonerUseCase
import com.plznoanr.domain.usecase.summoner.DeleteSummonerUseCase
import com.plznoanr.domain.usecase.summoner.GetSummonerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSummonerUseCase: GetSummonerUseCase,
    private val deleteAllSummonerUseCase: DeleteAllSummonerUseCase,
    private val deleteSummonerUseCase: DeleteSummonerUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val insertProfileUseCase: InsertProfileUseCase,
    private val getKeyUseCase: GetKeyUseCase,
    private val insertKeyUseCase: InsertKeyUseCase,
    private val deleteKeyUseCase: DeleteKeyUseCase
) : BaseViewModel<MainContract.UiState, MainContract.Event, MainContract.Effect>() {

    override fun setInitialState(): MainContract.UiState = MainContract.UiState(
        data = emptyList()
    )

    override fun handleEvents(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnLoad -> {
                getSummonerList()
                getProfile()
                getKey()
            }
            is MainContract.Event.OnSearch -> setEffect { MainContract.Effect.Navigation.ToSearch }
            is MainContract.Event.Refresh -> {}
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

    private fun getSummonerList() {
        viewModelScope.launch {
            getSummonerUseCase(Unit)
                .onStart { setState { copy(isLoading = true) } }
                .catch { setState { copy(error = it.message, isLoading = false) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                data = it.asReversed(),
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

    private fun deleteAllSummoner() {
        viewModelScope.launch {
            deleteAllSummonerUseCase(Unit)
                .onStart { setState { copy(isLoading = true) } }
                .catch { setState { copy(error = it.message, isLoading = false) } }
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
                .catch { setState { copy(error = it.message, isLoading = false) } }
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

    private fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase(Unit)
                .onStart { setState { copy(isLoading = true) } }
                .catch { setState { copy(error = it.message, isLoading = false) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                profile = it,
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
                .catch { setState { copy(error = it.message, isLoading = false) } }
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

    private fun getKey() {
        viewModelScope.launch {
            getKeyUseCase(Unit)
                .onStart { setState { copy(isLoading = true) } }
                .catch { setState { copy(error = it.message, isLoading = false) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                key = it,
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
                .catch { setState { copy(error = it.message, isLoading = false) } }
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
                .catch { setState { copy(error = it.message, isLoading = false) } }
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