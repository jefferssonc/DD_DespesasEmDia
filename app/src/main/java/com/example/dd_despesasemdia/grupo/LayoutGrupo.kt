package com.example.dd_despesasemdia.grupo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterUserGrupo
import com.example.dd_despesasemdia.models.UserGrupoModel

class LayoutGrupo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_grupo)


        val recyclerViewGrupoUser = findViewById<RecyclerView>(R.id.recyclerViewLayoutGrupo)
        recyclerViewGrupoUser.layoutManager = LinearLayoutManager(this)
        recyclerViewGrupoUser.setHasFixedSize(true)

        val listaUsersGrupo:MutableList<UserGrupoModel> = mutableListOf()
        val adapterUserGrupo = AdapterUserGrupo(this,listaUsersGrupo)

        recyclerViewGrupoUser.adapter = adapterUserGrupo

        val pessoa1 = UserGrupoModel("Fulano de Tal")
        val pessoa2 = UserGrupoModel("Tertuliano")
        val pessoa3 = UserGrupoModel("Mastrogilda")
        listaUsersGrupo.add(pessoa1)
        listaUsersGrupo.add(pessoa2)
        listaUsersGrupo.add(pessoa3)
    }
}