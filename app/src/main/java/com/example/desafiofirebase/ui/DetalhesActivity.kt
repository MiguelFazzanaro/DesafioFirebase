package com.example.desafiofirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofirebase.R
import com.example.desafiofirebase.models.Games
import com.example.desafiofirebase.service.collectionReference
import kotlinx.android.synthetic.main.activity_detalhes.*

class DetalhesActivity : AppCompatActivity() {

    lateinit var game: Games

    val viewModelDetalhe by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(collectionReference) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        var idGame = intent.getStringExtra("game")


        viewModelDetalhe.collectionReference.document(idGame.toString()).get().addOnSuccessListener { documentSnapshot ->
            game = documentSnapshot.toObject(Games::class.java)!!
            game.id = idGame.toString()

            tvDetalheNome.text = game.nome
            tvDetalheAno.text = game.data
            tvDetalheDescricao.text = game.descricao




        }





        fbEditGame.setOnClickListener {
            var gameClick = game.id
            var intent = Intent(this, CadastroGamesActivity::class.java)
            intent.putExtra("game", gameClick)
            startActivity(intent)
        }
    }
}