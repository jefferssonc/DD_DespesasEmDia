package com.example.dd_despesasemdia.grupo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.MainActivity
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterUserGrupo
import com.example.dd_despesasemdia.models.UserGrupoModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LayoutGrupo : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_grupo)

        voltarParaPrincipal()

        val textoDoItem = intent.getStringExtra("textoDoItem")
        val btnAdicionar = findViewById<ImageButton>(R.id.btnAddIntegranteLayoutGrupo)
        val btnDespesa = findViewById<ImageButton>(R.id.btnDespesasLayoutGrupo)

        btnAdicionar.setOnClickListener {
            val intent = Intent(this, AdicionarPessoaGrupo::class.java)
            intent.putExtra("textoDoItem", textoDoItem)
            startActivity(intent)
        }

        val textDespesaTotal = findViewById<TextView>(R.id.txtDespesaGrupoLayoutGrupo)
        val btnSair= findViewById<Button>(R.id.btnSairGrupoLayoutGrupo)

        val recyclerViewGrupoUser = findViewById<RecyclerView>(R.id.recyclerViewLayoutGrupo)
        recyclerViewGrupoUser.layoutManager = LinearLayoutManager(this)
        recyclerViewGrupoUser.setHasFixedSize(true)

        val listaUsersGrupo:MutableList<UserGrupoModel> = mutableListOf()
        val adapterUserGrupo = AdapterUserGrupo(this,listaUsersGrupo)
        recyclerViewGrupoUser.adapter = adapterUserGrupo

        db.collection("Grupo").document(textoDoItem.toString()).collection("Despesa")
            .get().addOnSuccessListener { documents->
                var somaDespesa = 0.0
                for(document in documents){
                    val valor = document.getDouble("Valor") ?: 0.0
                    somaDespesa += valor
                }
                textDespesaTotal.text = somaDespesa.toString()
            }


        db.collection("Grupo").document(textoDoItem.toString())
            .get().addOnSuccessListener { documents ->
                if (documents.exists()) {
                    val arrayItens = documents.get("Participantes") as ArrayList<String>
                    for (item in arrayItens) {
                        val nome = item
                        recyclerViewGrupoUser.adapter = adapterUserGrupo

                        val pessoa = UserGrupoModel(nome)
                        listaUsersGrupo.add(pessoa)
                    }
                }
            }

//        btnSair.setOnClickListener { view->

//            db.collection("Grupo").document(textoDoItem.toString())
//                .get().addOnSuccessListener {document->
//                    val itens = document.get("Participantes").toObjects<String>()
//                    val indiceDoItem = itens.indexOfFirst{
//
//                    }
//                    arrayItens.removeAt(indiceDoItem)
//                }
//        }

        btnDespesa.setOnClickListener { view->
            val intent = Intent(this, DespesasGrupo::class.java)
            intent.putExtra("textoDoItem", textoDoItem)
            startActivity(intent)
        }


    }


    private fun voltarParaPrincipal(){
        val btnvoltar = findViewById<ImageButton>(R.id.btnHomeLayoutGrupo)

        btnvoltar.setOnClickListener{view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}