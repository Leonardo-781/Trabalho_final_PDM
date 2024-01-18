package com.example.dropto

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    private lateinit var createAccountButton: Button
    private lateinit var emailTextField : EditText
    private lateinit var senhaTextField : EditText
    private lateinit var buttonEntrar : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        createAccountButton = findViewById(R.id.createAccountButton)
        emailTextField = findViewById(R.id.emailTextField)
        senhaTextField = findViewById(R.id.senhaTextField)
        buttonEntrar = findViewById(R.id.buttonEntrar)

        buttonEntrar.setOnClickListener(){ view ->
            val email = emailTextField.text.toString()
            val senha = senhaTextField.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { autenticacao ->
                    if (autenticacao.isSuccessful) {
                        openHomePage()
                    }
                }.addOnFailureListener {
                    val snackbar = Snackbar.make(view, "Erro ao fazer o login!", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }
    }



    fun openCadastroPage(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun openHomePage() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null) {
            openHomePage()
        }
    }
}

