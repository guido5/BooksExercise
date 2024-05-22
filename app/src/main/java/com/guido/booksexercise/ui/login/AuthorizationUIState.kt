package com.guido.booksexercise.ui.login

sealed class AuthorizationUIState {
    object Loading: AuthorizationUIState()
    object SuccessLogin : AuthorizationUIState()
    data class ErrorLogin(val isAlertEnable: Boolean, val message: String?) : AuthorizationUIState()
}