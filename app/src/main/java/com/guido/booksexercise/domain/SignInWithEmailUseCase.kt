package com.guido.booksexercise.domain

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guido.booksexercise.ui.login.FirebaseState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SignInWithEmailUseCase @Inject constructor() : FirebaseAuthUseCase(){

    fun signIn(mailTextFiel: String, passwordTextField: String, firebaseState: FirebaseState) {
        Firebase.auth.signInWithEmailAndPassword(mailTextFiel, passwordTextField)
            .addOnCompleteListener {
               handlerResult(it, firebaseState)
            }
    }
}