package com.example.desafiofirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofirebase.R
import com.example.desafiofirebase.models.Games
import com.example.desafiofirebase.service.collectionReference
import kotlinx.android.synthetic.main.activity_cadastro_games.*
import kotlinx.android.synthetic.main.activity_main.*

class CadastroGamesActivity : AppCompatActivity() {

    val viewModelCadastro by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(collectionReference) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_games)

        btSaveGame.setOnClickListener {
                var newGame: Games = Games(tvNewGameName.text.toString(),
                    tvNewGameDate.text.toString(),
                    tvNewGameDescription.text.toString(),
                    "123"
                )
                viewModelCadastro.newGame(newGame)
                startActivity(Intent(this, MainActivity::class.java))
        }
    }


}