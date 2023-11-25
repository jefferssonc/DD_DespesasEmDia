package com.example.dd_despesasemdia.individual

import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.example.dd_despesasemdia.MainActivity
import com.example.dd_despesasemdia.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class AdicionarDespesa : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_despesa)

        voltarParaInicial()
        irParaDespesa()

        val valor = findViewById<EditText>(R.id.inputValorAdicionarDespesa)
        val categoria = findViewById<EditText>(R.id.inputCategoriaAdicionarDespesa)
        val btadicionar = findViewById<ImageButton>(R.id.btnAddDespesaAdicionarDespesa)

        btadicionar.setOnClickListener { view ->

            val catmap = hashMapOf(
                "Nome" to categoria.text.toString(),
            )
            db.collection("Usuarios").document("U" + user?.displayName).collection("Categorias")
                .document(categoria.text.toString())
                .set(catmap).addOnFailureListener { exception ->
                    val erro = when (exception) {
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        else -> "Erro ao adicionar"
                    }
                    val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }


            val texto = valor.text.toString()
            val formatarData = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            val data = formatarData.format(Calendar.getInstance().time)

            val usersmap = hashMapOf(
                "Valor" to texto.toDoubleOrNull(),
                "Categoria" to categoria.text.toString(),
                "Conta" to user?.displayName,
                "Data"  to data,
                "timestamp" to Timestamp.now()
            )
            db.collection("Usuarios").document("U" + user?.displayName).collection("Despesas")
                .document()
                .set(usersmap).addOnCompleteListener {
                    val snackbar = Snackbar.make(view, "Despesa adicionada", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.GREEN)
                    snackbar.show()
                }.addOnFailureListener {exception ->
                    val erro = when (exception) {
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        else -> "Erro ao adicionar"
                    }
                    val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }

            val docRef = db.collection("Grupo")
            docRef.whereArrayContains("Participantes", user?.displayName.toString())
                .get().addOnSuccessListener {documents ->
                    for(document in documents){
                        val idDoDocumento = document.id
                        docRef.document(idDoDocumento).collection("Despesa").document().set(usersmap)
                    }
                }.addOnFailureListener {exception ->
                    val erro = when (exception) {
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        else -> "Erro ao adicionar"
                    }
                    val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
        }
    }

    private fun voltarParaInicial(){
        val btvoltar = findViewById<ImageButton>(R.id.btnHomeAdicionarDespesa)

        btvoltar.setOnClickListener{view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun irParaDespesa(){
        val btvoltar = findViewById<ImageButton>(R.id.btnDespesaAdicionarDespesa)

        btvoltar.setOnClickListener{view ->
            val intent = Intent(this, LayoutDespesas::class.java)
            startActivity(intent)
        }
    }



}
