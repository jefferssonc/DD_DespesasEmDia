package com.example.dd_despesasemdia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.adapters.AdapterDespesaPrincipal
import com.example.dd_despesasemdia.models.DespesaModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViewMainPag = findViewById<RecyclerView>(R.id.recyclerViewMainActivity)
        recyclerViewMainPag.layoutManager = LinearLayoutManager(this)
        recyclerViewMainPag.setHasFixedSize(true)

        val listaDespesaPagInicial : MutableList<DespesaModel> = mutableListOf()
        val adapterDespesaPagInicial = AdapterDespesaPrincipal(this,listaDespesaPagInicial)
        recyclerViewMainPag.adapter = adapterDespesaPagInicial

        val despesa1 = DespesaModel("Lazer", "10/03/1970","R$ 10.25")
        val despesa2 = DespesaModel("Outros", "10/03/1978","R$ 105.25")

        listaDespesaPagInicial.add(despesa1)
        listaDespesaPagInicial.add(despesa2)

    }
}