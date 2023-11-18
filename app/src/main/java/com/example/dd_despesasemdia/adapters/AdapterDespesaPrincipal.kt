package com.example.dd_despesasemdia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.models.DespesaModel

class AdapterDespesaPrincipal(private val context:Context, private val despesasMain: MutableList<DespesaModel>): RecyclerView.Adapter<AdapterDespesaPrincipal.DespesaPrincipalViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DespesaPrincipalViewHolder {
        val itemDespesaPrincipal = LayoutInflater.from(context).inflate(R.layout.despesa_main_item,parent, false)
        val holderDespesaPrincipal = DespesaPrincipalViewHolder(itemDespesaPrincipal)
        return holderDespesaPrincipal
    }

    override fun getItemCount(): Int {
        return despesasMain.size

    }

    override fun onBindViewHolder(holder: DespesaPrincipalViewHolder, position: Int) {
        holder.nomeDespesaMainItem.text = despesasMain[position].nome
        holder.valorDespesaMainItem.text = despesasMain[position].preco
        holder.dataDespesaMainItem.text = despesasMain[position].data
    }
    inner class DespesaPrincipalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeDespesaMainItem = itemView.findViewById<TextView>(R.id.nomeDespesaMainItem)
        val valorDespesaMainItem = itemView.findViewById<TextView>(R.id.valorDespesaMainItem)
        val dataDespesaMainItem = itemView.findViewById<TextView>(R.id.dataDespesaMainItem)

    }

}