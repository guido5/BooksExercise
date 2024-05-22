package com.guido.booksexercise.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.guido.booksexercise.domain.SignInWithEmailUseCase
import com.guido.booksexercise.domain.SignInWithGoogleUseCase
import com.guido.booksexercise.domain.SignUpWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel
@Inject constructor(app: Application,
                    private val signInWithEmailUseCase: SignInWithEmailUseCase,
                    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
                    private val signInWithGoogleUseCase: SignInWithGoogleUseCase) : AndroidViewModel(app), FirebaseState {

    private val _uiState =
        MutableStateFlow<AuthorizationUIState>(AuthorizationUIState.ErrorLogin(false, null))
    val uiState: StateFlow<AuthorizationUIState> = _uiState

    fun signInWithEmail(mailTextFiel: String, passwordTextField: String) {
        _uiState.value = AuthorizationUIState.Loading
        signInWithEmailUseCase.signIn(mailTextFiel, passwordTextField, this@AuthenticationViewModel)
    }

    fun signUpWithEmail(mailTextFiel: String, passwordTextField: String) {
        _uiState.value = AuthorizationUIState.Loading
        signUpWithEmailUseCase.signUp(mailTextFiel, passwordTextField, this@AuthenticationViewModel)
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            _uiState.value = AuthorizationUIState.Loading
            signInWithGoogleUseCase.login(this@AuthenticationViewModel)
        }
    }

    override fun onFirebaseAuthSuccessfully() {
       _uiState.value = AuthorizationUIState.SuccessLogin
    }

    override fun onFirebaseAuthError(ex: Exception) {
        val message = when(ex) {
            is FirebaseAuthInvalidCredentialsException -> {"El usuario o contraseña es incorrecto. Por favor intentalo de nuevo"}
            is FirebaseAuthUserCollisionException -> {"El usuario ya se encuentra registrado"}
            else -> {"Ocurrio un error inesperado. Por favor vuelve a intentarlo"}
        }
        _uiState.value = AuthorizationUIState.ErrorLogin(true, message)
    }

    fun dismiss() {
        _uiState.value = AuthorizationUIState.ErrorLogin(false, null)
    }
}

interface FirebaseState {
    fun onFirebaseAuthSuccessfully()
    fun onFirebaseAuthError(ex: Exception)
}
