package com.example.dropto

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log


class SecondRegisterActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    lateinit var userNameTextField : EditText
    lateinit var buttonFinalizar : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_register)

        userNameTextField = findViewById(R.id.userNameTextField)
        buttonFinalizar = findViewById(R.id.buttonFinalizar)

        buttonFinalizar.setOnClickListener() {
            val user = auth.currentUser
            val uid = user?.uid

            val name = userNameTextField.text.toString()

            if (uid != null) {
                val usuariosMap = hashMapOf(
                    "nome" to name
                )

                db.collection("Usuarios").document(uid)
                    .set(usuariosMap).addOnCompleteListener {
                        Log.d("db", "Sucesso ao salvar os dados do usuário no Firestore")
                    }.addOnFailureListener {
                        Log.e("db", "Erro ao salvar os dados do usuário no Firestore", it)
                    }
            } else {
                Log.e("db", "UID do usuário é nulo.")
            }

            openHomePage()
        }
    }

    fun openHomePage() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}