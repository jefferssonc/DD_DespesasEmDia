package com.example.dd_despesasemdia.grupo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterGrupos
import com.example.dd_despesasemdia.models.GruposModel

class TodosGrupos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos_grupos)


        val recyclerViewGrupos = findViewById<RecyclerView>(R.id.recyclerViewTodosGrupos)
        recyclerViewGrupos.layoutManager = LinearLayoutManager(this)
        recyclerViewGrupos.setHasFixedSize(true)

        val listaTodosGrupos : MutableList<GruposModel> = mutableListOf()
        val adapterTodosGrupos = AdapterGrupos(this,listaTodosGrupos)
        recyclerViewGrupos.adapter = adapterTodosGrupos

        val grupo1 = GruposModel("Jogo do Bicho")
        val grupo2 = GruposModel("Urubu do Pix")
        val grupo3 = GruposModel("Casa")
        val grupo4 = GruposModel("Casa da Sogra")
        val grupo5 = GruposModel("Biqueira")

        listaTodosGrupos.add(grupo1)
        listaTodosGrupos.add(grupo2)
        listaTodosGrupos.add(grupo3)
        listaTodosGrupos.add(grupo4)
        listaTodosGrupos.add(grupo5)




    }
}