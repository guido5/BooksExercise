package com.guido.booksexercise.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.guido.booksexercise.R
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
                MainView()
                val state = loginViewModel.uiState.collectAsState()
                when(state.value) {
                    is AuthorizationUIState.ErrorLogin -> {
                        val alertEnable =
                            (state.value as AuthorizationUIState.ErrorLogin).isAlertEnable
                        ResultDialog(show = alertEnable)
                    }
                    is AuthorizationUIState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    AuthorizationUIState.SuccessLogin -> {
                        val intent = Intent(this@AutenticationActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    @Composable fun MainView() {
        var mailTextFiel by remember { mutableStateOf("") }
        var passwordTextField by rememberSaveable { mutableStateOf("") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp)) {

                    OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = mailTextFiel, onValueChange = {
                        mailTextFiel = it
                    }, label = {
                        Text(text = getString(R.string.mail_title))
                    },)
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                        value = passwordTextField,
                        onValueChange = { passwordTextField = it },
                        label = { Text(text = getString(R.string.pass_title)) },
                        singleLine = true,
                        placeholder = { Text(getString(R.string.pass_title)) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Please provide localized description for accessibility services
                            val description = if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = {passwordVisible = !passwordVisible}){
                                Icon(imageVector  = image, description)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp), verticalAlignment = Alignment.CenterVertically) {
                        EmailButtonSignIn(modifier = Modifier
                            .fillMaxSize()
                            .weight(1f), mailTextFiel, passwordTextField)
                        Spacer(modifier = Modifier.width(8.dp))
                        EmailButtonSignUp(modifier = Modifier
                            .fillMaxSize()
                            .weight(1f), mailTextFiel, passwordTextField)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    GoogleButtonSignIn()
                }
            }
        }
    }

    private @Composable
    fun EmailButtonSignUp(modifier: Modifier, mailTextFiel: String, passwordTextField: String) {
        Button(onClick = {
            loginViewModel.signUpWithEmail(mailTextFiel, passwordTextField)
        }, modifier = modifier) {
            Text(text = "Registrarse" )
        }
    }

    private @Composable
    fun EmailButtonSignIn(modifier: Modifier,  mailTextFiel: String, passwordTextField: String) {
        Button(onClick = {
            loginViewModel.signInWithEmail(mailTextFiel, passwordTextField)
        }, modifier = modifier) {
            Text(text = "Iniciar Sesion" )
        }
    }

    @Composable
    fun GoogleButtonSignIn() {
        Button(onClick = {
                     loginViewModel.signInWithGoogle()
        }, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)) {
            Text(text = "Continuar con Google" )
        }
    }

    @Composable fun ResultDialog(show: Boolean) {
        if(show) {
            AlertDialog(onDismissRequest = { /*TODO*/ },
                confirmButton = {
                    TextButton(onClick = { loginViewModel.dismiss() }) {
                        Text(text = "Aceptar")
                    }
                },
                title = { Text(text = getString(R.string.auth_error)) },
                text = { Text(getString(R.string.auth_description)) })
        }

    }
}