package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        // OnClick listener to navigate to the registration page/activity.
        val register = findViewById<Button>(R.id.btnRegister)
        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // OnClick listener to navigate the login /page activity.
        // Displays Toast message if already signed in.
        val login = findViewById<Button>(R.id.btnLogin)
        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // If the user is not logged in, brings them to login page.
            if(FirebaseAuth.getInstance().currentUser == null) {
                startActivity(intent)
            }
            else {  // Else displays that they are already logged in.
                val toast = Toast.makeText(applicationContext, "Already logged in.", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        // OnClick listener to sign out.
        val logout = findViewById<Button>(R.id.btnLogout)
        logout.setOnClickListener {
            // If there is no user signed in, display that they are already signed out.
            if (FirebaseAuth.getInstance().currentUser == null) {
                val toast = Toast.makeText(applicationContext, "Already signed out.", Toast.LENGTH_SHORT)
                toast.show()
            } else { // Else sign the current user out.
                FirebaseAuth.getInstance().signOut()
                val toast = Toast.makeText(applicationContext, "Sign out successful.", Toast.LENGTH_SHORT)
                finish()
                startActivity(getIntent())
                toast.show()
            }
        }

        // OnClick listener to submit a game.
        val submit = findViewById<Button>(R.id.btnSubmitGame)
        submit.setOnClickListener {
            // If the user is not signed in, display a message saying they must be.
            if (FirebaseAuth.getInstance().currentUser == null) {
                val toast = Toast.makeText(applicationContext, "You must be signed in to submit a game.", Toast.LENGTH_SHORT)
                toast.show()
            }
            else { // Else navigate to the page.
                val intent = Intent(this, SubmitGameActivity::class.java)
                startActivity(intent)
            }
        }

        // OnClick listener to view submitted games.
        val view = findViewById<Button>(R.id.btnViewGames)
        view.setOnClickListener {
            // If the user is not signed in, display a message saying they must be.
            if (FirebaseAuth.getInstance().currentUser == null) {
                val toast = Toast.makeText(applicationContext, "You must be signed in to view games.", Toast.LENGTH_SHORT)
                toast.show()
            }
            else { // Else navigate to the page.
                val intent = Intent(this, ViewGamesActivity::class.java)
                startActivity(intent)
            }
        }

        // Sets text at bottom of dashboard based on login status.
        val loggedIn = findViewById<TextView>(R.id.txtLoggedIn)
        if (FirebaseAuth.getInstance().currentUser == null) {
            loggedIn.setText("Not currently logged in.")
        } else {
            loggedIn.setText("Currently logged in as: " + FirebaseAuth.getInstance().currentUser?.email)
        }
    }
}