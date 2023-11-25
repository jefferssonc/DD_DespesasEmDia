package com.example.dd_despesasemdia.grupo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.graphics.Color
import android.widget.EditText
import android.widget.ImageButton
import com.example.dd_despesasemdia.MainActivity
import com.example.dd_despesasemdia.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class AdicionarPessoaGrupo : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_pessoa_grupo)

        voltarParaPrincipal()

        val textoDoItem = intent.getStringExtra("textoDoItem")

        val editEmail = findViewById<EditText>(R.id.inputTxtAddPessoaGrupo)
        val btnAdicionar = findViewById<Button>(R.id.btnAddPessoaNoGrupo)

        btnAdicionar.setOnClickListener { view ->
            db.collection("Grupo").document(textoDoItem.toString())
                .update("Participantes", FieldValue.arrayUnion(editEmail.text.toString()))
                .addOnSuccessListener {
                    val snackbar = Snackbar.make(view, "Usuario adicionado", Snackbar.LENGTH_SHORT)
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
        }
    }

    private fun voltarParaPrincipal(){
        val btnvoltar = findViewById<ImageButton>(R.id.btnVoltarAddPessoaGrupo)

        btnvoltar.setOnClickListener{view ->
            val intent = Intent(this, LayoutGrupo::class.java)
            startActivity(intent)
        }
    }
}