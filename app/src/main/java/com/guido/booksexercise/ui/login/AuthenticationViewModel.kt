package com.guido.booksexercise.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.guido.booksexercise.domain.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel
@Inject constructor(app: Application,
                    private val signInWithGoogleUseCase: SignInWithGoogleUseCase) : AndroidViewModel(app), FirebaseState {

    private val _uiState = MutableStateFlow<AuthorizationUIState>(AuthorizationUIState.Init)
    val uiState: StateFlow<AuthorizationUIState> = _uiState

    fun signInWithGoogle() {
        viewModelScope.launch {
            _uiState.value = AuthorizationUIState.Loading
            signInWithGoogleUseCase.login(this@AuthenticationViewModel)
        }
    }

    override fun onFirebaseAuthSuccessfully() {
       _uiState.value = AuthorizationUIState.SuccessLogin
    }

    override fun onFirebaseAuthError() {
        _uiState.value = AuthorizationUIState.ErrorLogin
    }
}

interface FirebaseState {
    fun onFirebaseAuthSuccessfully()
    fun onFirebaseAuthError()
}
