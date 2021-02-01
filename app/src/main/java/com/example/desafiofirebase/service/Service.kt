package com.example.desafiofirebase.service

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

var database = FirebaseFirestore.getInstance()
var collectionReference: CollectionReference = database.collection("games")
