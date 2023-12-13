package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalproject.databinding.ActivitySubmitGameBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SubmitGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubmitGameBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubmitGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmitGameFinal.setOnClickListener {
            if(binding.txtGameName.text.toString() == "" ||
                binding.txtPlatform.text.toString() == "" ||
                binding.txtHours.text.toString() == "" ||
                binding.txtRating.text.toString() == "") {
                Toast.makeText(this, "Please ensure all input fields are filled in.", Toast.LENGTH_SHORT).show()
            }
            else {
                val gameName = binding.txtGameName.text.toString()
                val platform = binding.txtPlatform.text.toString()
                val hours = binding.txtHours.text.toString().toInt()
                val rating = binding.txtRating.text.toString().toInt()
                val comments = binding.txtComments.text.toString()

                databaseReference = FirebaseDatabase.getInstance().getReference("Games")
                val gameData = Game(gameName, platform, hours, rating, comments)

                databaseReference.child(gameName).setValue(gameData).addOnSuccessListener {
                    Toast.makeText(this, "Game Successfully Submitted.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SubmitGameActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "There was an error during submission.", Toast.LENGTH_SHORT).show()
                }
            }
            }

    }
}