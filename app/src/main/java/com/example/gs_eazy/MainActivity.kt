package com.example.gs_eazy

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gs_eazy.Api.Api
import com.example.gs_eazy.Model.Estado
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var nomeInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var senhaInput: EditText
    private lateinit var telefoneInput: EditText
    private lateinit var spinnerEstado: Spinner
    private lateinit var buttonCadastrar: Button
    private lateinit var estadoSelecionado: String
    private lateinit var api: Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        api = Api(this)

        nomeInput = findViewById(R.id.id_input_name)
        emailInput = findViewById(R.id.id_email_input)
        telefoneInput = findViewById(R.id.id_input_telefone)
        senhaInput = findViewById(R.id.id2_senha_input)
        spinnerEstado = findViewById(R.id.spinner_estado)
        buttonCadastrar = findViewById(R.id.id_entrar_button)

        val estados = Estado.values().map { it.nome }

        val adapter = ArrayAdapter(this, R.layout.spinner_item, estados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstado.adapter = adapter

        spinnerEstado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val nomeSelecionado = estados[position]
                estadoSelecionado = Estado.fromNomePersonalizado(nomeSelecionado)?.name ?: ""
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                estadoSelecionado = ""
            }
        }

        buttonCadastrar.setOnClickListener {
            val nome = nomeInput.text.toString()
            val email = emailInput.text.toString()
            val senha = senhaInput.text.toString()
            val telefone = telefoneInput.text.toString()
            val estado = estadoSelecionado

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || telefone.isEmpty() || estado.isEmpty()) {
                Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
            } else {

                val usuarioRequest = JSONObject().apply {
                    put("usuario", nome)
                    put("email", email)
                    put("senha", senha)
                    put("telefone", telefone)
                    put("estado", estado)
                }

                api.post("/usuario", usuarioRequest, { response ->
                    Log.d("RequestBody", "Usuário cadastrado com sucesso: $response")
                    Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show()

                    if (response is JSONObject) {
                        val userId = response.getString("id")
                    }

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }, { error ->

                    Log.d("RequestBody", "$usuarioRequest")
                    Log.e("Error", "Erro ao cadastrar usuário: $error")
                    Toast.makeText(this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show()
                })
        }

        val goToLogin = findViewById<TextView>(R.id.id_go_to_login)

        goToLogin.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}}