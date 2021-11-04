package com.example.mviconcept

sealed class MainIntent {

    object AddNumber : MainIntent()
    object ClearData : MainIntent()
}