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
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.userProfileChangeRequest

class CriarConta : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        val nome_user = findViewById<EditText>(R.id.inputNomeCriarConta)
        val email = findViewById<EditText>(R.id.inputEmailCriarConta)
        val senha = findViewById<EditText>(R.id.inputSenhaCriarConta)
        val btcriar_conta = findViewById<Button>(R.id.btnCriarConta)


        btcriar_conta.setOnClickListener { view ->
            if (nome_user.text.isNotBlank() || email.text.isNotBlank() || senha.text.isNotBlank()) {
                    auth.createUserWithEmailAndPassword(
                        email.text.toString(),
                        senha.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            auth.signInWithEmailAndPassword(email.text.toString(), senha.text.toString()).addOnCompleteListener { login ->
                                if (login.isSuccessful) {
                                    val user = FirebaseAuth.getInstance().currentUser
                                    val profileUpdates = userProfileChangeRequest {
                                        displayName = nome_user.text.toString()
                                    }
                                    user!!.updateProfile(profileUpdates)
                                }
                            }

                            val intent = Intent(this, LoginLayout::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        val erro = when (exception) {
                            is FirebaseAuthWeakPasswordException -> "Senha deve ter pelo menos 6 digítos"
                            is FirebaseAuthInvalidCredentialsException -> "Email inválido"
                            is FirebaseAuthUserCollisionException -> "Email já cadastrado"
                            is FirebaseNetworkException -> "Sem conexão com a internet"
                            else -> "Erro ao realizar cadastro"
                        }
                        val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
            }
        }
    }
}