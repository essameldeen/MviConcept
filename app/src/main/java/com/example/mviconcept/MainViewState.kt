package com.example.mviconcept

sealed class MainViewState {
    // list of state that maybe show to user

    //ideal
    object Idle : MainViewState()

    //Result
    data class Result(val number: Int) : MainViewState()

    // Error
    data class Error(val errorMessage: String) : MainViewState()
}