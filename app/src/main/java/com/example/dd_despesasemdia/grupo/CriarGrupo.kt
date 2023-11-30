package com.example.dd_despesasemdia.grupo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.dd_despesasemdia.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CriarGrupo : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_grupo)

        val btnCriar = findViewById<Button>(R.id.btnAdicionar)
        val editNome = findViewById<EditText>(R.id.inputTxtCriarGrupo)

        btnCriar.setOnClickListener { view ->
            val catmap = hashMapOf(
                "Nome" to editNome.text.toString(),
                "Participantes" to arrayListOf(user?.displayName)
            )
            db.collection("Grupo").document(editNome.text.toString()).set(catmap).addOnCompleteListener {
                val snackbar = Snackbar.make(view, "Grupo criado", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }.addOnFailureListener { exception ->
                val erro = when (exception) {
                    is FirebaseNetworkException -> "Sem conexão com a internet"
                    else -> "Erro ao adicionar"
                }
                val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
        }
    }
}