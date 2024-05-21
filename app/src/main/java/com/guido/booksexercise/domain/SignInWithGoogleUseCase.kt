package com.guido.booksexercise.domain

import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guido.booksexercise.domain.credentialManager.GetCredentials
import com.guido.booksexercise.ui.login.FirebaseState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@ViewModelScoped
class SignInWithGoogleUseCase
@Inject constructor(val getCredentials: GetCredentials) {
    private val TAG = SignInWithGoogleUseCase::class.java.canonicalName
    suspend fun login(firebaseState: FirebaseState) {
        getCredentials.result.catch { e ->
            firebaseState.onFirebaseAuthError()
        }.collect {
            val credential = it.credential
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    try {
                        Firebase.auth
                            .signInWithCredential(GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null))
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    firebaseState.onFirebaseAuthSuccessfully()
                                } else {
                                    firebaseState.onFirebaseAuthError()
                                }
                            }
                    } catch (e: ApiException) {
                        e.printStackTrace()
                    }
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e(TAG, "Received an invalid google id token response", e)
                }
            } else {
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }

}
