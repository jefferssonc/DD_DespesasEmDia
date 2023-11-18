package com.example.dd_despesasemdia.individual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterLayoutDespesas
import com.example.dd_despesasemdia.models.DespesaLayoutDespesasModel

class LayoutDespesas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_despesas)


        val recyclerViewLayoutDespesas = findViewById<RecyclerView>(R.id.recyclerViewLayoutDespesas)
        recyclerViewLayoutDespesas.layoutManager = LinearLayoutManager(this)
        recyclerViewLayoutDespesas.setHasFixedSize(true)


        val listaDespesasLayout : MutableList<DespesaLayoutDespesasModel> = mutableListOf()
        val adapterDespesasLayout = AdapterLayoutDespesas(this,listaDespesasLayout)

        recyclerViewLayoutDespesas.adapter = adapterDespesasLayout

        val despesa1 = DespesaLayoutDespesasModel(
            R.drawable.icon_alimentacao,
            "Alimentação"
        )
        val despesa2 = DespesaLayoutDespesasModel(
            R.drawable.icon_alimentacao,
            "Alimentação"
        )
        val despesa3 = DespesaLayoutDespesasModel(
            R.drawable.icon_alimentacao,
            "Alimentação"
        )
        val despesa4 = DespesaLayoutDespesasModel(
            R.drawable.lazer_icon,
            "Lazer"
        )
        val despesa5 = DespesaLayoutDespesasModel(
            R.drawable.outros_icon,
            "Jogo do Bicho"
        )
        listaDespesasLayout.add(despesa1)
        listaDespesasLayout.add(despesa2)
        listaDespesasLayout.add(despesa3)
        listaDespesasLayout.add(despesa4)
        listaDespesasLayout.add(despesa5)
    }
}