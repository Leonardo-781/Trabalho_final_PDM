package com.example.dropto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var buttonDeslogar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        buttonDeslogar = findViewById(R.id.buttonDeslogar)

        buttonDeslogar.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            val voltarLogin = Intent(this, LoginActivity::class.java)
            startActivity(voltarLogin)
            finish()
        }
    }
}
