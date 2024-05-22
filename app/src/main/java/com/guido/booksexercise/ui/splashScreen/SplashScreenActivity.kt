package com.guido.booksexercise.ui.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guido.booksexercise.ui.home.MainActivity
import com.guido.booksexercise.ui.login.AutenticationActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var splashScreen: SplashScreen
    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            true
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = if (Firebase.auth.currentUser != null)
                        Intent(this, MainActivity::class.java)
                    else
                        Intent(this, AutenticationActivity::class.java)
        startActivity(intent)
        finish()
    }

}