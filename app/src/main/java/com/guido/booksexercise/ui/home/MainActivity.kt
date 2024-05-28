package com.guido.booksexercise.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guido.booksexercise.data.Books
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guido.booksexercise.ui.login.AutenticationActivity
import com.guido.booksexercise.ui.theme.BooksExerciseTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel : MainViewModel by viewModels()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksExerciseTheme {
                // A surface container using the 'background' color from the theme
                BottomAppBarConfigure()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable fun BottomAppBarConfigure() {
        var navigationSelectedItem by remember {
            mutableStateOf(0)
        }
        val navController = rememberNavController()
        Scaffold(Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar {
                    BottomOptions.entries.forEachIndexed { index, bottomOptions ->
                        NavigationBarItem(selected = index == navigationSelectedItem,
                            onClick = {
                                navigationSelectedItem = index
                                navController.navigate(bottomOptions.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        navigationSelectedItem = index
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            label = {
                                    Text(text = bottomOptions.label)
                            },
                            icon = {
                                Icon(bottomOptions.icon, contentDescription = bottomOptions.label)
                            })
                    }
                }
            }) {
            NavHost(
                navController = navController,
                startDestination = BottomOptions.MY_BOOKS.route
            ) {
                composable(BottomOptions.MY_BOOKS.route) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = BottomOptions.MY_BOOKS.label)
                    }
                }

                composable(BottomOptions.FAVORITES.route) {
                    Text(text = BottomOptions.FAVORITES.label)
                }

                composable(BottomOptions.SEARCH.route) {
                    SearchView(mainViewModel)
                }

                composable(BottomOptions.SETTINGS.route) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        SettingsOptions()
                    }
                }
            }
        }
    }

    @Composable
    fun SettingsOptions() {
        Column {
            val current = LocalContext.current
            Button(onClick = {
                signOut()
            }) {
                Text(text = "Salir")
            }
        }
    }

    private fun signOut() {
        Firebase.auth.signOut()
        lifecycleScope.launch {
            CredentialManager.create(this@MainActivity).clearCredentialState(ClearCredentialStateRequest())
        }
        startActivity(Intent(this, AutenticationActivity::class.java))
        finish()
    }
}
