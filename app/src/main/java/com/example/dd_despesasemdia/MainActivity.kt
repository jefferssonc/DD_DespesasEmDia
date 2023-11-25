package com.example.dd_despesasemdia

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_despesasemdia.adapters.AdapterDespesaPrincipal
import com.example.dd_despesasemdia.grupo.TodosGrupos
import com.example.dd_despesasemdia.individual.AdicionarDespesa
import com.example.dd_despesasemdia.individual.LayoutDespesas
import com.example.dd_despesasemdia.models.DespesaModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<View>(R.id.view2)

        irParaGrupos()
        irParaAdd()
        irParaDespesa()
        voltarParaPrincipal()

        val recyclerViewMainPag = findViewById<RecyclerView>(R.id.recyclerViewMainActivity)
        recyclerViewMainPag.layoutManager = LinearLayoutManager(this)
        recyclerViewMainPag.setHasFixedSize(true)

        val listaDespesaPagInicial : MutableList<DespesaModel> = mutableListOf()
        val adapterDespesaPagInicial = AdapterDespesaPrincipal(this,listaDespesaPagInicial)
        recyclerViewMainPag.adapter = adapterDespesaPagInicial


        db.collection("Usuarios").document("U" + user?.displayName).collection("Categorias")
            .get().addOnSuccessListener { collectonDespesas ->
                for (documents in collectonDespesas.documents) {
                    val categoriaNome =  documents.getString("Nome")
                    val categoriaId = documents.id
                    db.collection("Usuarios")
                        .document("U" + user?.displayName)
                        .collection("Despesas")
                        .whereEqualTo("Categoria", categoriaId)
                        .get()
                        .addOnSuccessListener { QuerySnapshot ->
                            var categoriaSoma = 0.0
                            var despesaData = ""
                            for (itemDoc in QuerySnapshot.documents) {
                                val data = itemDoc.getString("Data")
                                val valor = itemDoc.getDouble("Valor") ?: 0.0
                                categoriaSoma += valor
                                despesaData = "Ultima adicao " + data
                            }

                            recyclerViewMainPag.adapter = adapterDespesaPagInicial
                            val despesa = DespesaModel(
                                categoriaNome.toString(),
                                despesaData,
                                categoriaSoma.toString()
                            )
                            listaDespesaPagInicial.add(despesa)
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

    @SuppressLint("SetTextI18n")
    private fun somaDespesa(){
        val view = findViewById<View>(R.id.view)

        db.collection("Usuarios").document("U" + user?.displayName)
            .collection("Despesas").get()
            .addOnSuccessListener { querySnapshot ->
                var soma = 0
                for (document in querySnapshot) {
                    val valor = document.getLong("Valor")?.toInt() ?: 0
                    soma += valor
                }
                val txtdespesa = findViewById<TextView>(R.id.txtDespesaPerfilIndividual)
                txtdespesa.text = "Despesa Total: $soma R$"
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
        val btvoltar = findViewById<ImageButton>(R.id.btnHomePerfilIndividual)

        btvoltar.setOnClickListener{view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun irParaAdd() {
        val btvoltar = findViewById<ImageButton>(R.id.btnAddDespesaPerfilIndividual)

        btvoltar.setOnClickListener { view ->
            val intent = Intent(this, AdicionarDespesa::class.java)
            startActivity(intent)
        }
    }

    private fun irParaDespesa(){
        val btvoltar = findViewById<ImageButton>(R.id.btnDespesasPerfilIndividual)

        btvoltar.setOnClickListener { view ->
            val intent = Intent(this, LayoutDespesas::class.java)
            startActivity(intent)
        }
    }

    private fun irParaGrupos(){
        val btvoltar = findViewById<ImageButton>(R.id.btnGrupoPerfilIndividual)

        btvoltar.setOnClickListener { view ->
            val intent = Intent(this, TodosGrupos::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val txtnome = findViewById<TextView>(R.id.txtUserNamePerfil)
        val username = user?.displayName
        txtnome.text = username
        somaDespesa()
    }

}