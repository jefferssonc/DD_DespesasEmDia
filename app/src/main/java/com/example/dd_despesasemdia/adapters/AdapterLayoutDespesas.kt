package com.example.dd_despesasemdia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.models.DespesaLayoutDespesasModel

class AdapterLayoutDespesas(private val context:Context, private val despesasLayout:MutableList<DespesaLayoutDespesasModel>): RecyclerView.Adapter<AdapterLayoutDespesas.LayoutDespesasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayoutDespesasViewHolder {
        val itemDespesasLayout = LayoutInflater.from(context).inflate(R.layout.layout_despesas_item,parent,false)
        val holder = LayoutDespesasViewHolder(itemDespesasLayout)
        return holder

    }

    override fun onBindViewHolder(holder: LayoutDespesasViewHolder, position: Int) {
        holder.foto.setImageResource(despesasLayout[position].foto)
        holder.nome.text = despesasLayout[position].nome
    }

    override fun getItemCount(): Int {
        return despesasLayout.size

    }
    inner class LayoutDespesasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto = itemView.findViewById<ImageView>(R.id.imgLayoutDespesasItem)
        val nome = itemView.findViewById<TextView>(R.id.txtNomeDespesaLayoutDespesasItem)


    }
}