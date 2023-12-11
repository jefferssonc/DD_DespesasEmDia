package com.example.dd_despesasemdia.individual

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterDespesaUnica
import com.example.dd_despesasemdia.models.DespesaUnicaModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DespesaUnica : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesa_unica)


        val recyclerViewDespesaUnica = findViewById<RecyclerView>(R.id.recyclerViewDespesaUnica)
        recyclerViewDespesaUnica.layoutManager = LinearLayoutManager(this)
        recyclerViewDespesaUnica.setHasFixedSize(true)

        val listaDespesasUnicas:MutableList<DespesaUnicaModel> = mutableListOf()
        val adapterDespesaUnicaPagInicial = AdapterDespesaUnica(this, listaDespesasUnicas)
        recyclerViewDespesaUnica.adapter = adapterDespesaUnicaPagInicial


        val textoDoItem = intent.getStringExtra("textoDoItem")
        val textView = findViewById<TextView>(R.id.txtNomeDaDespesaDespesaUnica)
        textView.text = textoDoItem
        val soma = findViewById<TextView>(R.id.txtValorTotalDespesaUnica)
        val view = findViewById<View>(R.id.scrollView3)

        db.collection("Usuarios")
            .document("U" + user?.displayName)
            .collection("Despesas")
            .whereEqualTo("Categoria", textoDoItem)
            .get()
            .addOnSuccessListener { QuerySnapshot ->
                var categoriaSoma = 0.0
                for (itemDoc in QuerySnapshot.documents) {
                    val valor = itemDoc.getDouble("Valor") ?: 0.0
                    categoriaSoma += valor
                }
                soma.text = categoriaSoma.toString()
            }.addOnFailureListener { exception ->
                val erro = when (exception) {
                    is FirebaseNetworkException -> "Sem conexão com a internet"
                    else -> "Falha na execução"
                }
                val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }

        db.collection("Usuarios").document("U" + user?.displayName)
            .collection("Despesas")
            .whereEqualTo("Categoria", textoDoItem)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents){
                    val data = document.getString("Data")
                    val valor = document.getDouble("Valor") ?: 0.0
                    val idDoDocumento = document.id

                    recyclerViewDespesaUnica.adapter = adapterDespesaUnicaPagInicial

                    adapterDespesaUnicaPagInicial.onItemClickListener = { textoDoItem2 ->
                        val intent = Intent(this, MenuDespesa::class.java)
                        intent.putExtra("textoDoItem", textoDoItem)
                        intent.putExtra("textoDoItem2", textoDoItem2)
                        startActivity(intent)
                    }

                    val despesa = DespesaUnicaModel(valor.toString(),
                        data.toString(),
                        idDoDocumento
                    )
                    listaDespesasUnicas.add(despesa)
                }
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