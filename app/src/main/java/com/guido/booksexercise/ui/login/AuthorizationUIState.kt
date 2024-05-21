package com.guido.booksexercise.ui.login

sealed class AuthorizationUIState {
    object Loading: AuthorizationUIState()

    object Init: AuthorizationUIState()
    object SuccessLogin : AuthorizationUIState()
    object ErrorLogin : AuthorizationUIState()
}