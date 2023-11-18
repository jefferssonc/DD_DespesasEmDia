package com.example.dd_despesasemdia.individual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterDespesaUnica
import com.example.dd_despesasemdia.models.DespesaUnicaModel

class DespesaUnica : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesa_unica)


        val recyclerViewDespesaUnica = findViewById<RecyclerView>(R.id.recyclerViewDespesaUnica)
        recyclerViewDespesaUnica.layoutManager = LinearLayoutManager(this)
        recyclerViewDespesaUnica.setHasFixedSize(true)

        val listaDespesasUnicas:MutableList<DespesaUnicaModel> = mutableListOf()
        val adapterDespesaUnicaPagInicial = AdapterDespesaUnica(this, listaDespesasUnicas)

        recyclerViewDespesaUnica.adapter = adapterDespesaUnicaPagInicial

        val despesaUnica1 = DespesaUnicaModel("R$ 19.56", "03/12/1896")
        val despesaUnica2 = DespesaUnicaModel("R$ 15.69", "10/11/1986")

        listaDespesasUnicas.add(despesaUnica1)
        listaDespesasUnicas.add(despesaUnica2)

    }
}