package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.finalproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.text.SpannableString
import android.text.style.UnderlineSpan

// Activity for login.
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase instance.
        firebaseAuth = FirebaseAuth.getInstance()

        // Styling link text that navigates to Register.
        val registerText = findViewById<TextView>(R.id.textView)
        val registerString = registerText.text.toString()
        val spannableString = SpannableString(registerString);
        spannableString.setSpan(UnderlineSpan(), 0, registerString.length, 0)
        registerText.text = spannableString

        // OnClick listener to navigate to Register from Login.
        binding.textView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // OnClick listener for login submission.
        binding.btnSubmit.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val pass = binding.txtPassword.text.toString()

            // If both fields are filled.
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Try to login using provided credentials.
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) { // If login is successful, direct back to main activity.
                        Toast.makeText(this, "Sign in successful.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else { // Else display the login was not successful.
                        val error = it.exception.toString()
                        val message = error.substring(error.indexOf(":") + 2)
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
            } else { // Else display that a field was not filled.
                Toast.makeText(this, "Empty fields are not allowed.", Toast.LENGTH_SHORT).show()

            }
        }
    }
}