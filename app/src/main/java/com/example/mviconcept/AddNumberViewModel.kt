package com.example.mviconcept

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class AddNumberViewModel : ViewModel() {
    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)
    private var _number = 0

    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState>
        get() = _viewState

    init {
        processIntent()
    }

    // process  intent
    private fun processIntent() {

        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.AddNumber -> addNumberIntent()
                    is MainIntent.ClearData -> clearDataIntent()

                }
            }
        }


    }

    // reduceIntent
    private fun addNumberIntent() {
        viewModelScope.launch {
            _viewState.value =
                try {
                    MainViewState.Result(++_number)
                } catch (e: Exception) {
                    MainViewState.Error(e.message!!)
                }

        }

    }

    private fun clearDataIntent() {
        viewModelScope.launch {
            _viewState.value =
                try {
                    MainViewState.Result(0)
                } catch (e: Exception) {
                    MainViewState.Error(e.message!!)
                }
        }
        _number = 0
    }
}
