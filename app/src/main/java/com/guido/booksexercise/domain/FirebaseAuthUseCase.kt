package com.guido.booksexercise.domain

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.guido.booksexercise.ui.login.FirebaseState

abstract class FirebaseAuthUseCase {
    fun handlerResult(task: Task<AuthResult>, firebaseState: FirebaseState ) {
        if(task.isSuccessful) {
            firebaseState.onFirebaseAuthSuccessfully()
        } else {
            task.exception?.let { it1 -> firebaseState.onFirebaseAuthError(it1) }
        }
    }
}
