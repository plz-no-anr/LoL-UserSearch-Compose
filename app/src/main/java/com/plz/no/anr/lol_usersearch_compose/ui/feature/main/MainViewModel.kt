package com.plz.no.anr.lol_usersearch_compose.ui.feature.main

import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.usecase.profile.InsertProfileUseCase
import com.plznoanr.domain.usecase.summoner.DeleteAllSummonerUseCase
import com.plznoanr.domain.usecase.summoner.DeleteSummonerUseCase
import com.plznoanr.domain.usecase.summoner.GetSummonerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainContract : BaseContract() {

    data class UiState(
        val data: List<Summoner>,
        val profile: Profile? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnLoad : Event()
        object OnSearch : Event()
        data class OnDelete(val name: String) : Event()
        data class OnAddProfile(val profile: Profile) : Event()
        object OnDeleteAll: Event()
        object Refresh : Event()
    }

    sealed class Effect : ViewSideEffect {

        sealed class Navigation : Effect() {
            object ToSearch : Navigation()
        }

    }
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSummonerUseCase: GetSummonerUseCase,
    private val deleteAllSummonerUseCase: DeleteAllSummonerUseCase,
    private val deleteSummonerUseCase: DeleteSummonerUseCase,
    private val insertProfileUseCase: InsertProfileUseCase
) : BaseViewModel<MainContract.UiState, MainContract.Event, MainContract.Effect>() {

    override fun setInitialState(): MainContract.UiState = MainContract.UiState(
        data = emptyList()
    )

    override fun handleEvents(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnLoad -> getSummonerList()
            is MainContract.Event.OnSearch -> {
                setEffect { MainContract.Effect.Navigation.ToSearch }
            }
            is MainContract.Event.Refresh -> {}
            is MainContract.Event.OnDeleteAll -> deleteAllSummoner()
            is MainContract.Event.OnDelete -> deleteSummoner(event.name)
            is MainContract.Event.OnAddProfile -> addProfile(event.profile)
        }
    }

    private fun getSummonerList() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getSummonerUseCase(Unit).collectLatest { result ->
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
            deleteAllSummonerUseCase(Unit).collectLatest { result ->
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
            deleteSummonerUseCase(name).collectLatest { result ->
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
            insertProfileUseCase(profile).collectLatest { result ->
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


}