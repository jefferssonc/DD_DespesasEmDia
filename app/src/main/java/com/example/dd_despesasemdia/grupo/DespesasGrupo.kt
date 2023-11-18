package com.example.dd_despesasemdia.grupo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterDespesaPrincipal
import com.example.dd_despesasemdia.models.DespesaModel

class DespesasGrupo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesas_grupo)


        //Utilizando o mesmo adapter e model do despesa unica principal, visto que as infos sao as mesmas

        val recyclerViewDespesasGrupo = findViewById<RecyclerView>(R.id.recyclerViewDespesasGrupo)
        recyclerViewDespesasGrupo.layoutManager = LinearLayoutManager(this)
        recyclerViewDespesasGrupo.setHasFixedSize(true)

        val listaDespesaPrincipalGrupo : MutableList<DespesaModel> = mutableListOf()
        val adapterDespesaPrincipalGrupo = AdapterDespesaPrincipal(this, listaDespesaPrincipalGrupo)
        recyclerViewDespesasGrupo.adapter = adapterDespesaPrincipalGrupo

        val despesa1 = DespesaModel("Lazer", "10/03/1970","R$ 10.25")
        val despesa2 = DespesaModel("Outros", "10/03/1978","R$ 105.25")
        val despesa3 = DespesaModel("Jogo do Bicho", "10/03/1978","R$ 105.25")

        listaDespesaPrincipalGrupo.add(despesa1)
        listaDespesaPrincipalGrupo.add(despesa2)
        listaDespesaPrincipalGrupo.add(despesa3)


    }
}