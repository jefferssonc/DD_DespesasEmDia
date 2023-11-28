package com.example.dd_despesasemdia

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class LoginLayout : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_layout)

        val btnLogin = findViewById<Button>(R.id.btnLoginLayout)
        val editEmail = findViewById<EditText>(R.id.inputEmailLoginLayout)
        val editSenha = findViewById<EditText>(R.id.inputSenhaLoginLayout)

        btnLogin.setOnClickListener {view ->
            if (editEmail.text.isNotBlank() || editSenha.text.isNotBlank()){
                auth.signInWithEmailAndPassword(editEmail.text.toString(), editSenha.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }.addOnFailureListener {exception ->
                    val erro = when(exception){
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        is FirebaseAuthInvalidCredentialsException -> "Email ou senha inválido"
                        else -> "Erro ao realizar login"

                    }
                    val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()

                }
            }
        }

    }
}