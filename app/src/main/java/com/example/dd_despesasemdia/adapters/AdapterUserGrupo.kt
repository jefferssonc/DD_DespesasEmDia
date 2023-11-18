package com.example.dd_despesasemdia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.models.UserGrupoModel

class AdapterUserGrupo(private val context: Context, private val usersGrupo:MutableList<UserGrupoModel>): RecyclerView.Adapter<AdapterUserGrupo.UserGrupoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGrupoViewHolder {
        val itemUserGrupo = LayoutInflater.from(context).inflate(R.layout.layout_grupo_user_item,parent,false)
        val holder = UserGrupoViewHolder(itemUserGrupo)
        return holder

    }

    override fun onBindViewHolder(holder: UserGrupoViewHolder, position: Int) {
        holder.nome.text =usersGrupo[position].nome
    }

    override fun getItemCount(): Int {
        return usersGrupo.size

    }
    inner class UserGrupoViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val nome = itemView.findViewById<TextView>(R.id.nomeUserLayoutGrupoUserItem)
    }
}