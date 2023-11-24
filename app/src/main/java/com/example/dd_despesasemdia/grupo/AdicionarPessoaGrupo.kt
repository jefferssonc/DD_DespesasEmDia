package com.example.dd_despesasemdia.grupo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dd_despesasemdia.R

class AdicionarPessoaGrupo : AppCompatActivity() {

//    private val db = FirebaseFirestore.getInstance()
//    val user = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_pessoa_grupo)

//        val editEmail = findViewById<EditText>(R.id.inputTxtAddPessoaGrupo)
//        val btnAdicionar = findViewById<Button>(R.id.btnAddPessoaNoGrupo)
//
//        btnAdicionar.setOnClickListener { view ->
//            db.collection("Grupo").document(grupoId)
//                .update("Participantes", FieldValue.arrayUnion(editEmail.text.toString()))
//                .addOnSuccessListener {
//                    val snackbar = Snackbar.make(view, "Usuario adicionado", Snackbar.LENGTH_SHORT)
//                    snackbar.setBackgroundTint(Color.RED)
//                    snackbar.show()
//                }.addOnFailureListener { exception ->
//                    val erro = when (exception) {
//                        is FirebaseNetworkException -> "Sem conexão com a internet"
//                        else -> "Falha na execução"
//                    }
//                    val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
//                    snackbar.setBackgroundTint(Color.RED)
//                    snackbar.show()
//                }
//        }

    }
}