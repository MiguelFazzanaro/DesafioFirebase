package com.example.desafiofirebase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase.R
import com.example.desafiofirebase.models.Games
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_games.view.*
import kotlinx.android.synthetic.main.item_games.view.*

class MainAdapter (var listaGames: ArrayList<Games>, val listener: OnGamesClickListener): RecyclerView.Adapter<MainAdapter.GamesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_games, parent,false)
        return GamesViewHolder(itemView)
    }

    override fun getItemCount() = listaGames.size

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        var lista = listaGames.get(position)

        Picasso.get().load(lista.imagem).resize(115,100).into(holder.img)
        holder.nome.text = lista.nome
        holder.data.text = lista.data
//        holder.descricao.text = lista.descricao

    }

    interface OnGamesClickListener{
        fun GamesClick(position: Int)
    }

    inner class GamesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val img: ImageView = itemView.ivGameImagem
        val nome: TextView = itemView.tvGameNome
        val data: TextView = itemView.tvGameAdicionado
//        val descricao: TextView = itemView.tvNewGameDescription

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position)
                listener.GamesClick(position)
        }
    }
}