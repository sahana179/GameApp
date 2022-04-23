package com.example.gamesapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import retrofit2.http.GET

class GamesAdapter(val data : List<GamesObject>, val context: Activity, private val onItemClickListener: View.OnClickListener) : RecyclerView.Adapter<GamesAdapter.ViewHolder>()
{

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
            val nameView: TextView = itemView.findViewById(R.id.textView)
            val logoImageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        //val itemView : View = LayoutInflater.from(context).inflate(R.layout.list_games, parent)
        val rowView : View = LayoutInflater
            .from(context)
            .inflate(R.layout.list_games, parent, false)

        rowView.setOnClickListener(onItemClickListener)
        val viewHolder = ViewHolder(rowView)
        return viewHolder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val character : GamesObject = data[position]
        holder.nameView.text = character.name
        Picasso.get().load(character.picture).into(holder.logoImageView)
        holder.itemView.tag = position // hidden
    }

    override fun getItemCount(): Int
    {
        return data.size
    }
}