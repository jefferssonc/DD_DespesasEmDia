package com.example.dd_despesasemdia.individual

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dd_despesasemdia.MainActivity
import com.example.dd_despesasemdia.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DeletarDespesa : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deletar_despesa)

        val textoDoItem = intent.getStringExtra("textoDoItem")
        val textoDoItem2 = intent.getStringExtra("textoDoItem2")
        val btnDeletar = findViewById<Button>(R.id.btnDeletar)

        btnDeletar.setOnClickListener {  view->
        val docRef = db.collection("Usuarios").document("U" + user?.displayName).collection("Despesas")
            docRef.document(textoDoItem2.toString()).delete().addOnSuccessListener {
                val snackbar = Snackbar.make(view, "Despesa deletada", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }.addOnFailureListener { exception ->
                val erro = when (exception) {
                    is FirebaseNetworkException -> "Sem conexão com a internet"
                    else -> "Falha na execução"
                }
                val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }

            docRef.whereEqualTo("Categoria", textoDoItem)
                .get().addOnSuccessListener {documents->
                    if(documents.isEmpty) {
                        db.collection("Usuarios").document("U" + user?.displayName).collection("Categorias")
                            .document(textoDoItem.toString()).delete()
                    }
                }.addOnFailureListener { exception ->
                    val erro = when (exception) {
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        else -> "Falha na execução"
                    }
                    val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
                }

    }
}