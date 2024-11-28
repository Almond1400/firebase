package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etConfPass: EditText
    private lateinit var etPass: EditText
    private lateinit var btnSignUp: Button
    lateinit var tvRedirectLogin: TextView
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) {
                v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                systemBars.bottom)
            insets
        }
        etEmail = findViewById(R.id.email)
        etConfPass = findViewById(R.id.confirm_password)
        etPass = findViewById(R.id.password)
        btnSignUp = findViewById(R.id.registr)
        tvRedirectLogin = findViewById(R.id.tvRedirectLogin)
        auth = Firebase.auth
        btnSignUp.setOnClickListener {
            singUpUser()
        }
        tvRedirectLogin.setOnClickListener{
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun singUpUser() {

        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val confPass = etConfPass.text.toString()

        if (email.isBlank() || pass.isBlank() || confPass.isBlank()) {
            Toast.makeText(this, "Почта и пароль не могут быть пустыми",
                Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confPass) {
            Toast.makeText(this, "Пароль и пароль подтверждения не совпадают", Toast.LENGTH_SHORT)
                .show()
                return
        }

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this,"Регистрация успешна",Toast.LENGTH_SHORT).show()
                        intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this,"Ошибка регистрации",Toast.LENGTH_SHORT).show()
            }
        }
    }
}