package com.ryzen30xx.bookapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.ProgressBar

public final class LoginActivity: AppCompatActivity()  {
    var email: EditText? = null
    var password: EditText? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.editText_email)
        password = findViewById(R.id.editTextTextPassword)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        var lbl_email : TextView = findViewById(R.id.lbl_email)
        var lbl_password : TextView = findViewById(R.id.lbl_password)
        var lbl_register : TextView = findViewById(R.id.lbl_register)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)


        btnLogin.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE
            val email = email?.text.toString()
            val password = password?.text.toString()

            if (email.isEmpty()) {
                lbl_email.text ="Please enter your email"
                lbl_email.setTextColor(Color.parseColor("#FF0000"));
                progressBar.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE

            }
            if (password.isEmpty()) {
                lbl_password.text = "Please enter your password"
                lbl_password.setTextColor(Color.parseColor("#FF0000"));
                progressBar.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
            }
        }
    }
}

