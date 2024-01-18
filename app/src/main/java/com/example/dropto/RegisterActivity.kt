package com.example.dropto

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class RegisterActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()

    private lateinit var loginAccountButton : TextView
    private lateinit var emailTextField : EditText
    private lateinit var senhaTextField : EditText
    private lateinit var confirmarSenhaTextField : EditText
    private lateinit var buttonCadastrar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loginAccountButton = findViewById(R.id.loginAccountButton)
        buttonCadastrar = findViewById(R.id.buttonCadastrar)
        emailTextField = findViewById(R.id.emailTextField)
        senhaTextField = findViewById(R.id.senhaTextField)
        confirmarSenhaTextField = findViewById(R.id.confirmarSenhaTextField)

        buttonCadastrar.setOnClickListener() { view ->
            val email = emailTextField.text.toString()
            val senha = senhaTextField.text.toString()
            val confirmSenha = confirmarSenhaTextField.toString()

            if (email.isEmpty() || senha.isEmpty() || confirmSenha.isEmpty()) {
                val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            } else  {
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
                    if (cadastro.isSuccessful) {
                        val user = auth.currentUser
                        val uid = user?.uid


                        val snackbar = Snackbar.make(view, "Usuario cadastrado com sucesso!", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.BLUE)
                        snackbar.setTextColor(Color.WHITE)
                        snackbar.show()
                        emailTextField.text.clear()
                        senhaTextField.text.clear()

                        openSecondRegisterPage(uid)
                    }
                }.addOnFailureListener() { exception ->
                    val mensagemErro = when(exception) {
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo seis caracteres!"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email válido!"
                        is FirebaseAuthUserCollisionException -> "Essa conta já está cadastrada!"
                        is FirebaseNetworkException -> "Sem conexão com a internet!"
                        else -> "Erro ao cadastrar usuário!"
                    }
                    val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()
                }
            }
        }

    }

    fun openLoginPage(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun openSecondRegisterPage(uid: String?) {
        val intent = Intent(this, SecondRegisterActivity::class.java)
        intent.putExtra("uid", uid)
        startActivity(intent)
        finish()
    }
}