package com.example.dd_despesasemdia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.models.GruposModel

class AdapterGrupos(private val context:Context, private val gruposLayout:MutableList<GruposModel>):RecyclerView.Adapter<AdapterGrupos.GruposViewHolder>() {

    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GruposViewHolder {
        val itemTodosGrupos = LayoutInflater.from(context).inflate(R.layout.todos_grupos_item,parent,false)
        val holderTodosGrupos = GruposViewHolder(itemTodosGrupos)
        return holderTodosGrupos
    }

    override fun onBindViewHolder(holder: GruposViewHolder, position: Int) {
        holder.nomeGrupo.text = gruposLayout[position].nome


    }

    override fun getItemCount(): Int {
        return gruposLayout.size
    }

    inner class GruposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nomeGrupo = itemView.findViewById<TextView>(R.id.txtGrupoNameTodosGruposItem)
        val icone = itemView.findViewById<ImageView>(R.id.iconTodosGruposItem)

        init {
            icone.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val itemClicado = gruposLayout[position]
                    onItemClickListener?.invoke(itemClicado.nome)
                }
            }
        }

    }
}