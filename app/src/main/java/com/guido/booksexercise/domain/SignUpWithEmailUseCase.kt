package com.guido.booksexercise.domain

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guido.booksexercise.ui.login.FirebaseState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SignUpWithEmailUseCase @Inject constructor() : FirebaseAuthUseCase(){
    fun signUp(mailTextFiel: String, passwordTextField: String, firebaseState: FirebaseState) {
        Firebase.auth.createUserWithEmailAndPassword(mailTextFiel, passwordTextField)
            .addOnCompleteListener {
                handlerResult(it, firebaseState)
            }
    }

}
