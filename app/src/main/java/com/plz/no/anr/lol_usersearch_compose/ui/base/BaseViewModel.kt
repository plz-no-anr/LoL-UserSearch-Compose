package com.plz.no.anr.lol_usersearch_compose.ui.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber


abstract class BaseViewModel<UiState : BaseContract.UiState, in Intent : BaseContract.Intent, SideEffect : BaseContract.SideEffect>
    : ViewModel() {

    abstract fun setInitialState(): UiState

    abstract fun handleEvents(intent: Intent)

    private val initialState: UiState by lazy { setInitialState() }

    private val _uiState: MutableState<UiState> = mutableStateOf(initialState)
    val uiState: State<UiState> = _uiState

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow()

    private val _sideEffect: Channel<SideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val exceptionHandler by lazy {
        CoroutineExceptionHandler { _, t ->
            Timber.e(t)
        }
    }

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch(exceptionHandler) {
            _intent.collect {
                handleEvents(it)
            }
        }
    }

    fun setIntent(intent: Intent) {
        viewModelScope.launch(exceptionHandler) { _intent.emit(intent) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = uiState.value.reducer()
        _uiState.value = newState
    }

    protected fun setEffect(builder: () -> SideEffect) {
        val effectValue = builder()
        viewModelScope.launch(exceptionHandler) { _sideEffect.send(effectValue) }
    }

}