package com.example.desafiofirebase.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiofirebase.models.Games
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.activity_main.*

class MainViewModel(val collectionReference: CollectionReference): ViewModel() {

    var retornoGames = ArrayList<Games>()

    fun newGame (game: Games){
        collectionReference.document().set(game)
    }

    fun update (id: String, game: Games){

    }





}

