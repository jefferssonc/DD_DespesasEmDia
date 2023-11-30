package com.example.dd_despesasemdia.grupo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterDespesaPrincipal
import com.example.dd_despesasemdia.models.DespesaModel
import com.google.firebase.firestore.FirebaseFirestore

class DespesasGrupo : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesas_grupo)

        val textoDoItem = intent.getStringExtra("textoDoItem")

        //Utilizando o mesmo adapter e model do despesa unica principal, visto que as infos sao as mesmas

        val recyclerViewDespesasGrupo = findViewById<RecyclerView>(R.id.recyclerViewDespesasGrupo)
        recyclerViewDespesasGrupo.layoutManager = LinearLayoutManager(this)
        recyclerViewDespesasGrupo.setHasFixedSize(true)

        val listaDespesaPrincipalGrupo : MutableList<DespesaModel> = mutableListOf()
        val adapterDespesaPrincipalGrupo = AdapterDespesaPrincipal(this, listaDespesaPrincipalGrupo)

        db.collection("Grupo").document(textoDoItem.toString()).collection("Despesa")
            .get().addOnSuccessListener {documents->
                for(document in documents){
                    val nome = document.getString("Categoria")
                    val valor = document.getDouble("Valor") ?: 0.0
                    val data = document.getString("Data")
                    recyclerViewDespesasGrupo.adapter = adapterDespesaPrincipalGrupo
                    val despesa = DespesaModel(nome.toString(),
                        data.toString(),
                        valor.toString())
                    listaDespesaPrincipalGrupo.add(despesa)
                }

            }

    }
}