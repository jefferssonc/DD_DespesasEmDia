package com.example.dd_despesasemdia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.models.DespesaUnicaModel


class AdapterDespesaUnica(private val context:Context, private val despesasUnicas:MutableList<DespesaUnicaModel>) : RecyclerView.Adapter<AdapterDespesaUnica.DespesaUnicaViewHolder>() {

    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DespesaUnicaViewHolder {
        val itemDespesaUnica = LayoutInflater.from(context).inflate(R.layout.despesa_unica_item,parent,false)
        val holderDespesaUnica = DespesaUnicaViewHolder(itemDespesaUnica)
        return holderDespesaUnica
    }

    override fun getItemCount(): Int {
        return despesasUnicas.size

    }

    override fun onBindViewHolder(holder: DespesaUnicaViewHolder, position: Int) {
//        holder.valorDespesaUnicaItem.text = despesasUnicas[position].valor
//        holder.dataDespesaUnicaItem.text = despesasUnicas[position].data
//        holder.idDespesaUnicaItem.text = despesasUnicas[position].id
        val despesa = despesasUnicas[position]
        holder.bind(despesa)

    }
    inner class DespesaUnicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val valorDespesaUnicaItem = itemView.findViewById<TextView>(R.id.valorDespesaUnicaItem)
        val dataDespesaUnicaItem = itemView.findViewById<TextView>(R.id.dataDespesaUnicaItem)
        val idDespesaUnicaItem = itemView.findViewById<TextView>(R.id.idDespesaUnicaItem)
        val botao = itemView.findViewById<ImageButton>(R.id.btnDeletarDespesaUnicaItem)

        fun bind(despesa: DespesaUnicaModel) {
            dataDespesaUnicaItem.text = despesa.data
            valorDespesaUnicaItem.text = despesa.valor
            idDespesaUnicaItem.text = despesa.id
        }
            init {
                botao.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val itemClicado = despesasUnicas[position]
                        onItemClickListener?.invoke(itemClicado.id)
                    }
                }
            }


        }
}