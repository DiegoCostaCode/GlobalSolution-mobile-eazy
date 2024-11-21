package com.example.gs_eazy

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gs_eazy.Api.Api
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var senhaInput: EditText
    private lateinit var entrarButton: Button
    private lateinit var api: Api

    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "MyAppPrefs"
    private val USER_EMAIL_KEY = "user_email"
    private val USER_SENHA_KEY = "user_senha"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        api = Api(this)

        emailInput = findViewById(R.id.id_email_input)
        senhaInput = findViewById(R.id.id2_senha_input)
        entrarButton = findViewById(R.id.id_entrar_button)

        val goBackToMain = findViewById<ImageView>(R.id.id_voltar_icon)

        goBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        entrarButton.setOnClickListener {
            val email = emailInput.text.toString()
            val senha = senhaInput.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
            }
            else {
                val loginRequest = JSONObject().apply {
                    put("email", email)
                    put("senha", senha)
                }

                api.post("/usuario/login", loginRequest, { response ->
                    Log.d("RequestBody", "Login realizado com sucesso.")
                    Toast.makeText(this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show()


                    with(sharedPreferences.edit()) {
                        putString(USER_EMAIL_KEY, email)
                        putString(USER_SENHA_KEY, senha)
                        apply()
                    }

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()

                }, { error ->
                    Log.e("Error", "Erro ao realizar login: $error")
                    Toast.makeText(this, "Erro ao realizar login", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}