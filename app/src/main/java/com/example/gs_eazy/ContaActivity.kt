package com.example.gs_eazy

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gs_eazy.Api.Api
import com.example.gs_eazy.Model.Conta
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ContaActivity : AppCompatActivity() {

    private lateinit var contaAdapter: ContaAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddItem: Button
    private lateinit var api: Api

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_conta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        api = Api(this)
        contaAdapter = ContaAdapter(mutableListOf())
        btnAddItem = findViewById(R.id.btn_add_item)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contaAdapter


        val goBackToHome = findViewById<ImageView>(R.id.id2_voltar_icon)

        goBackToHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnAddItem.setOnClickListener {
            contaAdapter.addConta(Conta(0, 0.0, 0.0, ""))
        }

        buscarContasUsuario()
    }

    private fun buscarContasUsuario() {
        api.get("/conta/usuario/2", { response ->
            try {
                val contasJsonArray = response as JSONArray
                val contasList = mutableListOf<Conta>()

                for (i in 0 until contasJsonArray.length()) {
                    val contaJson = contasJsonArray.getJSONObject(i)
                    val conta = Conta(
                        id = contaJson.getInt("id"),
                        kwh = contaJson.getDouble("kwh"),
                        valor = contaJson.getDouble("valor"),
                        data = contaJson.getString("data")
                    )
                    contasList.add(conta)
                }

                runOnUiThread {
                    contaAdapter.receberContas(contasList)
                }

            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("ContasActivity", "Erro ao processar resposta JSON: ${e.message}")
            }
        }, { error ->
            Log.e("ContasActivity", "Erro ao buscar contas: $error")
        })
    }

     fun removeConta(conta: Conta) {
        api.delete("/conta/${conta.id}", { response ->
            Log.d("ContaAdapter", "Conta deletada com sucesso: $response")
            runOnUiThread {
                Toast.makeText(this, "Conta deletada com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }, { error ->
            Log.e("ContaAdapter", "Erro ao deletar a conta: $error")
        })
    }

     fun updateConta(conta: Conta) {
        val jsonBody = JSONObject().apply {
            put("kwh", conta.kwh)
            put("valor", conta.valor)
            put("data", conta.data)
        }

        api.update("/conta/${conta.id}", jsonBody, { response ->
            Log.d("ContaAdapter", response.toString())
            runOnUiThread {
                Toast.makeText(this, "Conta atualizada com sucesso!", Toast.LENGTH_SHORT).show()

            }
        }, { error ->
            Log.e("ContaAdapter", "Erro ao atualizar a conta: $error")
        })
    }

     fun adicionarConta(conta: Conta) {
        val jsonBody = JSONObject().apply {
            put("kwh", conta.kwh)
            put("valor", conta.valor)
            put("data", conta.data)
        }

        api.post("/conta/2", jsonBody, { response ->
            Log.d("ContaAdapter", "Conta adicionada com sucesso: $response")
            runOnUiThread {
                Toast.makeText(this, "Conta adicionada com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }, { error ->
            Log.e("ContaAdapter", "Erro ao adicionar a conta: $error")
        })
    }

}
