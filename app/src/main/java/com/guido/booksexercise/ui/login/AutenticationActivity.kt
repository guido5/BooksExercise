package com.guido.booksexercise.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guido.booksexercise.ui.home.MainActivity
import com.guido.booksexercise.ui.theme.BooksExerciseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AutenticationActivity : ComponentActivity() {

    private val loginViewModel: AuthenticationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksExerciseTheme {
                val state = loginViewModel.uiState.collectAsState()
                when(state.value) {
                    AuthorizationUIState.ErrorLogin -> {}
                    AuthorizationUIState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    AuthorizationUIState.SuccessLogin -> {
                        val intent = Intent(this@AutenticationActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    AuthorizationUIState.Init -> {
                        MainView()
                    }
                }
            }
        }
    }

    @Composable fun MainView() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Row {
                    GoogleButtonSignIn()
                }
            }
        }
    }

    @Composable
    fun GoogleButtonSignIn() {
        Button(onClick = {
                     loginViewModel.signIn()
        }, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)) {
            Text(text = "Continuar con Google" )
        }
    }
}