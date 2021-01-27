package com.example.desafiofirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofirebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_login.*

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btSave.setOnClickListener {
            getFields(
                tvNameRegister.text.toString(),
                tvEmailRegister.text.toString(),
                tvPassowrdRegister.text.toString(),
                tvRepeatPassowrdRegister.text.toString())

        }

    }

    fun getFields(username: String, email: String, password: String, rPassword: String){
        var usernameEmpty = username.isNotBlank()
        var emailEmpty = email.isNotBlank()
        var passwordEmpty = password.isNotBlank()
        var rPasswordEmpty = rPassword.isNotBlank()

        Log.i("tag", "$username, $email, $password, $rPassword")

        if(emailEmpty && passwordEmpty && usernameEmpty && rPasswordEmpty ){
            sendData(email,password)
        }
        else
            showMsg("Email ou senha incorretos. Verifique os campos e tente novamente")
    }

    fun sendData(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener ({task->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result?.user!!
                    showMsg("Usuario cadastrado")
                    startActivity(Intent(this, MainActivity::class.java))
                }
            })
    }
    fun showMsg (msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}