package com.example.finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.GameAdapter.MyViewHolder

class GameAdapter(var context: Context, var list: ArrayList<Game>) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.game, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val game = list[position]
        holder.gameName.text = game.gameName
        holder.platform.text = game.platform
        holder.hours.text = game.hours.toString()
        holder.score.text = game.score.toString()
        holder.comments.text = game.comments
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gameName: TextView
        var platform: TextView
        var hours: TextView
        var score: TextView
        var comments: TextView

        init {
            gameName = itemView.findViewById(R.id.tvGameName)
            platform = itemView.findViewById(R.id.tvPlatform)
            hours = itemView.findViewById(R.id.tvHours)
            score = itemView.findViewById(R.id.tvScore)
            comments = itemView.findViewById(R.id.tvComments)
        }
    }
}