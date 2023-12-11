package com.example.dd_despesasemdia.individual

import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.dd_despesasemdia.MainActivity
import com.example.dd_despesasemdia.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class MenuDespesa : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_despesa)

        val textoDoItem = intent.getStringExtra("textoDoItem")
        val textoDoItem2 = intent.getStringExtra("textoDoItem2")
        val btnDeletar = findViewById<Button>(R.id.btnDeletarDespesaDefinicoesGrupo)
        val btnAdicionar = findViewById<Button>(R.id.btnAddDefinicoesGrupo)
        val textCategoria = findViewById<TextView>(R.id.txtCategoriaDefinicoesDespesas)
        val textValor = findViewById<TextView>(R.id.txtValorDefinicoesDespesas)

        db.collection("Usuarios").document("U" + user?.displayName)
            .collection("Despesas").document(textoDoItem2.toString()).get().addOnSuccessListener {document->
                val valor = document.getLong("Valor")?.toInt() ?: 0
                val categoriaNome =  document.getString("Nome")
                textCategoria.text = categoriaNome
                textValor.text = valor.toString()
            }


        btnDeletar.setOnClickListener { view ->
            db.collection("Usuarios").document("U" + user?.displayName).collection("Despesas")
                .document(textoDoItem2.toString()).delete().addOnSuccessListener {
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

            db.collection("Usuarios").document("U" + user?.displayName).collection("Despesas")
                .whereEqualTo("Categoria", textoDoItem)
                .get().addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        db.collection("Usuarios").document("U" + user?.displayName)
                            .collection("Categorias")
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

            db.collection("Grupo").whereArrayContains("Participantes", user?.email.toString())
                .get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val idDoDocumento = document.id
                        db.collection("Grupo").document(idDoDocumento).collection("Despesa")
                            .whereEqualTo("Id", textoDoItem2)
                            .get().addOnSuccessListener { documents1 ->
                                for (document1 in documents1) {
                                    val idDoDocumentoDeletar = document1.id
                                    db.collection("Grupo").document(idDoDocumento)
                                        .collection("Despesa").document(idDoDocumentoDeletar)
                                        .delete()
                                }
                            }
                    }
                }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

                btnAdicionar.setOnClickListener { view ->

                    val formatarData = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                    val data = formatarData.format(Calendar.getInstance().time)

                    db.collection("Usuarios").document("U" + user?.displayName)
                        .collection("Despesas").document(textoDoItem2.toString())
                        .get()
                        .addOnSuccessListener { document ->
                            val valor = document.getDouble("Valor") ?: 0.0

                    val usersmap = hashMapOf(
                        "Valor" to valor,
                        "Categoria" to textoDoItem,
                        "Conta" to user?.email.toString(),
                        "Data" to data,
                        "Id" to textoDoItem2
                    )

                    val docRef = db.collection("Grupo")
                    docRef.whereArrayContains("Participantes", user?.email.toString())
                        .get().addOnSuccessListener { documents ->
                            for (document1 in documents) {
                                val idDoDocumento = document1.id
                                docRef.document(idDoDocumento).collection("Despesa").document()
                                    .set(usersmap)
                            }
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
}