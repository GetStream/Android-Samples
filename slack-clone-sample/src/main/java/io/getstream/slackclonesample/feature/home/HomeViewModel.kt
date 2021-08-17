package io.getstream.slackclonesample.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.getstream.slackclonesample.util.Factory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class HomeViewModel() : ViewModel() {

    private val actions = MutableSharedFlow<Action>()

    private val _events = MutableSharedFlow<Event>(extraBufferCapacity = 100)

    val events: Flow<Event>
        get() = _events.asSharedFlow()

    private val _state = MutableStateFlow(State(true))
    private val stateMutex = Mutex()

    val state: StateFlow<State>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            actions.collect { action ->
                when (action) {
                    is Action.OpenDrawerAction -> setState { copy(isDrawerOpen = true) }
                    is Action.CloseDrawerAction -> setState { copy(isDrawerOpen = false) }
                    is Action.SelectChannelAction -> setState { copy(
                        isDrawerOpen = false,
                        cid = action.cid
                    ) }
                    is Action.UserConnectedAction -> setState { copy(userId = action.userId) }
                }
            }
        }
    }

    fun submitAction(action: Action) {
        viewModelScope.launch {
            actions.emit(action)
        }
    }

    protected suspend fun setState(reducer: State.() -> State) {
        stateMutex.withLock {
            _state.value = reducer(_state.value)
        }
    }

    data class State(val isDrawerOpen: Boolean, val cid: String? = null, val userId: String? = null)

    sealed class Action {
        data class UserConnectedAction(val userId: String): Action()
        object OpenDrawerAction : Action()
        object CloseDrawerAction: Action()
        data class SelectChannelAction(val cid: String): Action()
    }

    sealed class Event {
    }
}

class HomeViewModelFactory() : Factory<HomeViewModel> {

    override fun create(): HomeViewModel {
        return HomeViewModel()
    }
}
