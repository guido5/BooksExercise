package com.guido.booksexercise.data.credentialManager

import android.content.Context
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.guido.booksexercise.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object CredentialManagerModule {
    @Provides
    fun provideGetGoogleIdOption(@ApplicationContext context: Context) : GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.WEB_CLIENT_ID))
            .setAutoSelectEnabled(true)
            .build()
    }
    @Provides
    fun provideGetCredentialRequest(googleIdOption: GetGoogleIdOption) : GetCredentialRequest {
        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }
}