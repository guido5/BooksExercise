package com.guido.booksexercise.domain

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guido.booksexercise.ui.login.FirebaseState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SignUpWithEmailUseCase @Inject constructor(){
    fun signUp(mailTextFiel: String, passwordTextField: String, firebaseState: FirebaseState) {
        Firebase.auth.createUserWithEmailAndPassword(mailTextFiel, passwordTextField)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    firebaseState.onFirebaseAuthSuccessfully()
                } else {
                    firebaseState.onFirebaseAuthError()
                }
            }
    }

}
