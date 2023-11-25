package com.example.dd_despesasemdia.adapters

import android.content.Context
import android.graphics.Color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.models.DespesaUnicaModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdapterDespesaUnica(private val context:Context, private val despesasUnicas:MutableList<DespesaUnicaModel>) : RecyclerView.Adapter<AdapterDespesaUnica.DespesaUnicaViewHolder>() {


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

        botao.setOnClickListener {view->
                val db = FirebaseFirestore.getInstance()
                val user = FirebaseAuth.getInstance().currentUser
                db.collection("Usuarios").document("U" + user?.displayName)
                    .collection("Despesas").document(idDespesaUnicaItem.toString())
                    .delete().addOnSuccessListener {
                        val snackbar = Snackbar.make(view, "Despesa deletada", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.GREEN)
                        snackbar.show()
                    }.addOnFailureListener { exception ->
                        val erro = when (exception) {
                            is FirebaseNetworkException -> "Sem conexão com a internet"
                            else -> "Falha na execução"
                        }
                        val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
                    }
        }
        }
}