package com.plznoanr.lol.core.mvibase

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import timber.log.Timber

interface MviState
interface MviEvent
interface MviSideEffect

abstract class MviViewModel<State : MviState, Event : MviEvent, SideEffect : MviSideEffect> :
    ViewModel() {

    private val tag = this@MviViewModel.javaClass.simpleName

    abstract val uiState: StateFlow<State>

    protected val eventFlow: MutableSharedFlow<Event> =
        MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE)

    private val sideEffectChannel: Channel<SideEffect> = Channel()
    val sideEffectFlow: Flow<SideEffect> = sideEffectChannel.receiveAsFlow()

    suspend fun onEvent(event: Event) {
        eventFlow.emit(event)
    }

    protected suspend fun postEffect(sideEffect: SideEffect) {
        sideEffectChannel.send(sideEffect)
    }

    override fun onCleared() {
        super.onCleared()
        sideEffectChannel.close()
    }

    protected fun Flow<Event>.sendEvent(
        function: suspend (Event) -> Unit
    ) = onEach {
        Timber.d("$tag::OnEventCall -> $it")
    }.onEach {
        function(it)
    }

    protected fun Flow<Event>.reduce(
        initialState: State,
        accumulator: (State, value: Event) -> State
    ) = scan(initialState) { state, event ->
        accumulator(state, event)
    }

    protected fun Flow<Event>.reduce(
        initialState: State,
        accumulator: (State) -> State
    ) = scan(initialState) { state, _ ->
        accumulator(state)
    }

}