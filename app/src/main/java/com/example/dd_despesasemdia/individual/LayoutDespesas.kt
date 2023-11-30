package com.example.dd_despesasemdia.individual

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.MainActivity
import com.example.dd_despesasemdia.R
import com.example.dd_despesasemdia.adapters.AdapterLayoutDespesas
import com.example.dd_despesasemdia.models.DespesaLayoutDespesasModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LayoutDespesas : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_despesas)

        voltarParaPrincipal()
        irParaAdd()

        val recyclerViewLayoutDespesas = findViewById<RecyclerView>(R.id.recyclerViewLayoutDespesas)
        recyclerViewLayoutDespesas.layoutManager = LinearLayoutManager(this)
        recyclerViewLayoutDespesas.setHasFixedSize(true)

        val listaDespesasLayout : MutableList<DespesaLayoutDespesasModel> = mutableListOf()
        val adapterDespesasLayout = AdapterLayoutDespesas(this,listaDespesasLayout)
        val view = findViewById<View>(R.id.view5)

        db.collection("Usuarios").document("U" + user?.displayName).collection("Categorias").get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot) {
                    val nome = document.getString("Nome")
                    recyclerViewLayoutDespesas.adapter = adapterDespesasLayout
                    val icone = when (nome.toString()) {
                        "Alimentacao" -> {
                            R.drawable.icon_alimentacao
                        }
                        "Transporte" -> {
                            R.drawable.despesa_transporte_icon
                        }
                        "Educacao" -> {
                            R.drawable.educacao_icon
                        }
                        "Saude" -> {
                            R.drawable.saude_icon
                        }
                        "Lazer" -> {
                            R.drawable.lazer_icon
                        }
                        else -> {
                            R.drawable.outros_icon
                        }
                    }
                    adapterDespesasLayout.onItemClickListener = { textoDoItem ->
                        val intent = Intent(this, DespesaUnica::class.java)
                        intent.putExtra("textoDoItem", textoDoItem)
                        startActivity(intent)
                    }
                    val despesa = DespesaLayoutDespesasModel(icone, nome.toString())

                    listaDespesasLayout.add(despesa)
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

    private fun voltarParaPrincipal(){
        val btvoltar = findViewById<ImageButton>(R.id.btnHomeLayoutDespesa)

        btvoltar.setOnClickListener{view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun irParaAdd(){
        val btvoltar = findViewById<ImageButton>(R.id.btnAddDespesaLayoutDespesa)

        btvoltar.setOnClickListener{view ->
            val intent = Intent(this, AdicionarDespesa::class.java)
            startActivity(intent)
        }
    }


}