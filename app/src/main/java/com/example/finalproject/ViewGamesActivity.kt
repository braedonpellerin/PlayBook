package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ActivityViewGamesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class ViewGamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewGamesBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var gameList: ArrayList<Game>
    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewGamesBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_view_games)

        databaseReference = FirebaseDatabase.getInstance().getReference("Games")
        recyclerView = findViewById(R.id.GameList)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        gameList = ArrayList<Game>()
        gameAdapter = GameAdapter(this, gameList)
        recyclerView.adapter = gameAdapter

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(childSnapshot in snapshot.children) {
                    val game = childSnapshot.getValue<Game>()
                    println(game)
                    game?.let { gameList.add(game) }
                }
                gameAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                throw Exception(error.toString())
            }
        })
    }
}