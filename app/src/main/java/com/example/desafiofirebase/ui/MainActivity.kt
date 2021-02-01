package com.example.desafiofirebase.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofirebase.R
import com.example.desafiofirebase.models.Games
import com.example.desafiofirebase.service.collectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainAdapter.OnGamesClickListener {

    val viewModelHome by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(collectionReference) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fbNewGame.setOnClickListener {
            var intent = Intent(this, CadastroGamesActivity::class.java)
            startActivity(intent)
        }

        viewModelHome.collectionReference.get().addOnSuccessListener { documents ->
//            var games = arrayListOf<Games>()
            for (document in documents){
                val populate: Games = document.toObject(Games::class.java)
                viewModelHome.retornoGames.add(populate)
            }
            rvGames.adapter = MainAdapter(viewModelHome.retornoGames, this)
            rvGames.setHasFixedSize(true)
        }
    }


//        reference.addValueEventListener(object : ValueEventListener {
//            var idGame = reference.push().key
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                dataSnapshot.children.forEach {
//                    val game: Games = it.toObject(Games::class.java)
//                    retornoGames.add(game)
//
//                }
//                Log.i("aaaa", retornoGames.toString())
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//    }

    override fun GamesClick(position: Int) {
        var gameClick = viewModelHome.retornoGames.get(position).nome
        var intent = Intent(this, DetalhesActivity::class.java)
        intent.putExtra("game", gameClick)
        startActivity(intent)
    }

//    fun ler() {
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                dataSnapshot.children.forEach {
//                    Log.i("ret", it.toString())
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("ler", "Failed to read value.", error.toException())
//            }
//        })
//    }
}


