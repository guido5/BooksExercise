package com.guido.booksexercise.domain.credentialManager

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class GetCredentials @Inject constructor(@ApplicationContext val context: Context,
                                         val request: GetCredentialRequest){
    val result : Flow<GetCredentialResponse> = flow {
            val result = CredentialManager.create(context).getCredential(
                request = request,
                context = context,
            )
            emit(result)
    }
}
