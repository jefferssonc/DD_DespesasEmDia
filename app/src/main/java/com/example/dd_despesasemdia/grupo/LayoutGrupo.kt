package com.example.dd_despesasemdia.grupo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterUserGrupo
import com.example.dd_despesasemdia.models.UserGrupoModel

class LayoutGrupo : AppCompatActivity() {
 //   val db = FirebaseFirestore.getInstance()
 //   val user = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_grupo)

//        val textParticipantes = findViewById<TextView>(R.id.textView5) //       val textDespesaTotal = findViewById<TextView>(R.id.txtDespesaGrupoLayoutGrupo)
//        val btnAdicionar = findViewById<ImageButton>(R.id.btnAddIntegranteLayoutGrupo)
//        val btnSair= findViewById<Button>(R.id.btnSairGrupoLayoutGrupo)

        val recyclerViewGrupoUser = findViewById<RecyclerView>(R.id.recyclerViewLayoutGrupo)
        recyclerViewGrupoUser.layoutManager = LinearLayoutManager(this)
        recyclerViewGrupoUser.setHasFixedSize(true)

        val listaUsersGrupo:MutableList<UserGrupoModel> = mutableListOf()
        val adapterUserGrupo = AdapterUserGrupo(this,listaUsersGrupo)

        recyclerViewGrupoUser.adapter = adapterUserGrupo



        val pessoa1 = UserGrupoModel("Fulano de Tal")
        listaUsersGrupo.add(pessoa1)

    }
}