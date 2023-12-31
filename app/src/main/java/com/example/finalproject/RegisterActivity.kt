package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.finalproject.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import android.text.SpannableString
import android.text.style.UnderlineSpan

// Activity for registration.
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase instance.
        firebaseAuth = FirebaseAuth.getInstance()

        // Styling link text that navigates to Login.
        val loginText = findViewById<TextView>(R.id.textView)
        val loginString = loginText.text.toString()
        val spannableString = SpannableString(loginString);
        spannableString.setSpan(UnderlineSpan(), 0, loginString.length, 0)
        loginText.text = spannableString

        // OnClick listener for text view - redirect to login activity.
        binding.textView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // OnClick listener for submit button.
        binding.btnSubmit.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val pass = binding.txtPassword.text.toString()
            val confirmPass = binding.txtConfirmPassword.text.toString()

            // If an email is entered and passwords match, creates a new user in Firebase.
            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Sign up successful. Please login with your created account.", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            val error = it.exception.toString()
                            val message = error.substring(error.indexOf(":") + 2)
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        }
                    }
                } else { // Else displays that the passwords do not match.
                    Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                }
            } else { // Else displays that empty fields are not permitted.
                Toast.makeText(this, "Empty fields are not allowed.", Toast.LENGTH_SHORT).show()

            }
        }
    }
}