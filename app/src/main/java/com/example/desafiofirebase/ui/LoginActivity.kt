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
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btLogin.setOnClickListener {
            getFields(tvEmailLogin.text.toString(), tvPassowrdLogin.text.toString())
        }

        tvCreateRegister.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }

    fun getFields(email: String, password: String) {
        var emailEmpty = email.isNotBlank()
        var passwordEmpty = password.isNotBlank()

        Log.i("tag", "${email}, $password")

        if (emailEmpty && passwordEmpty) {
            sendData(email, password)
        } else
            showMsg("Erro, verifique os campos ou Cadastre-se!")
    }

    fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun sendData(email: String, password: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result?.user!!
                    startActivity(Intent(this, MainActivity::class.java))
                } else
                    showMsg("Erro, verifique os campos ou Cadastre-se!")
            }
    }


}


