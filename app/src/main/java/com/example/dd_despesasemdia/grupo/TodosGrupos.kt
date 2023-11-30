package com.example.dd_despesasemdia.grupo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.MainActivity
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterGrupos
import com.example.dd_despesasemdia.individual.DespesaUnica
import com.example.dd_despesasemdia.models.GruposModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TodosGrupos : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos_grupos)

        criarGrupo()
        voltarParaPrincipal()


        val view = findViewById<View>(R.id.view10)

        val recyclerViewGrupos = findViewById<RecyclerView>(R.id.recyclerViewTodosGrupos)
        recyclerViewGrupos.layoutManager = LinearLayoutManager(this)
        recyclerViewGrupos.setHasFixedSize(true)

        val listaTodosGrupos : MutableList<GruposModel> = mutableListOf()
        val adapterTodosGrupos = AdapterGrupos(this,listaTodosGrupos)



        db.collection("Grupo").whereArrayContains("Participantes", user?.email.toString()).get().addOnSuccessListener { documents ->
            for(document in documents){
                val nome = document.getString("Nome")
                recyclerViewGrupos.adapter = adapterTodosGrupos

                adapterTodosGrupos.onItemClickListener = { textoDoItem ->
                    val intent = Intent(this, LayoutGrupo::class.java)
                    intent.putExtra("textoDoItem", textoDoItem)
                    startActivity(intent)
                }

                val grupo = GruposModel(nome.toString())
                listaTodosGrupos.add(grupo)
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

    }

    fun criarGrupo(){
        val btnCriar = findViewById<ImageButton>(R.id.btnAddGrupoTodosGrupos)

        btnCriar.setOnClickListener{view ->
            val intent = Intent(this, CriarGrupo::class.java)
            startActivity(intent)
        }
    }

    private fun voltarParaPrincipal(){
        val btnvoltar = findViewById<ImageButton>(R.id.btnVoltarTodosGrupos)

        btnvoltar.setOnClickListener{view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}