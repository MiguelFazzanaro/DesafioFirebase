package com.example.desafiofirebase.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofirebase.R
import com.example.desafiofirebase.models.Games
import com.example.desafiofirebase.service.collectionReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_cadastro_games.*
import java.util.*

class CadastroGamesActivity : AppCompatActivity() {

    lateinit var storageReference: StorageReference
    lateinit var game : Games
    val CODE_IMG = 1000
    var imagem = 1

    val viewModelCadastro by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(collectionReference) as T
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CODE_IMG){
            val uploadFile = storageReference.putFile(data!!.data!!)
            val task = uploadFile.continueWithTask{task ->
                if(task.isSuccessful)
                {
                    Toast.makeText(this, "Imagem Carrregada com sucesso!", Toast.LENGTH_SHORT).show()
                }
                storageReference!!.downloadUrl
            }.addOnCompleteListener{task->
                if(task.isSuccessful){
                    val downloadUri = task.result
                    val url = downloadUri!!.toString().substring(0, downloadUri.toString().indexOf("&token"))

                    viewModelCadastro.saveImage(downloadUri.toString())
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_games)

        var idGame = intent.getStringExtra("game")
        config()

        if(idGame != null){
            viewModelCadastro.collectionReference.document(idGame.toString()).get().addOnSuccessListener { documentSnapshot ->
                game = documentSnapshot.toObject(Games::class.java)!!
                game.id = idGame.toString()

                tvNewGameDescription.setText(game.descricao)
                tvNewGameDate.setText(game.data)
                tvNewGameName.setText(game.nome)

                fbTakePicture.setOnClickListener {
                    getRes()
                }

                btSaveGame.setOnClickListener {
                    val myRef = viewModelCadastro.collectionReference.document(idGame.toString())
                    myRef.update("descricao", tvNewGameDescription.text.toString())
                    myRef.update("data", tvNewGameDate.text.toString())
                    myRef.update("nome", tvNewGameName.text.toString())
                    myRef.update("imagem", viewModelCadastro.imagemGame.value.toString())
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        } else {
            fbTakePicture.setOnClickListener {

                getRes()

            }
            btSaveGame.setOnClickListener {
                var newGame: Games = Games(tvNewGameName.text.toString(),
                    tvNewGameDate.text.toString(),
                    tvNewGameDescription.text.toString(),
                    viewModelCadastro.imagemGame.value.toString()
                )
                viewModelCadastro.newGame(newGame)
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    fun config(){
        imagem = random()
        storageReference = FirebaseStorage.getInstance().getReference("img$imagem")
        Log.i("AAAAAA", imagem.toString())
    }

    fun getRes(){
        val intent = Intent()
        intent.type = "Imagens/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Image"), CODE_IMG)

    }

    fun random(): Int {
        var from = 1
        var to = 1000000
        val rand = Random()
        return rand.nextInt(to - from) + from
    }

}