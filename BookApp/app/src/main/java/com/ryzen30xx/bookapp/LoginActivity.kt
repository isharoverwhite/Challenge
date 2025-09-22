package com.ryzen30xx.bookapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth

public final class LoginActivity: AppCompatActivity()  {
    var email: EditText? = null
    var password: EditText? = null
    private lateinit var auth: FirebaseAuth
    auth = Firebase.auth
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
                return@setOnClickListener

            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                lbl_email.text = "Please enter a valid email address"
                lbl_email.setTextColor(Color.parseColor("#FF0000"));
                progressBar.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
                return@setOnClickListener
            } else if (email.length < 5) {
                lbl_email.text = "Please enter a valid email address"
                lbl_email.setTextColor(Color.parseColor("#FF0000"));
                progressBar.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
                return@setOnClickListener
            } else {
                lbl_email.text = "Email"
                lbl_email.setTextColor(Color.parseColor("#000000"));
            }

            if (password.isEmpty()) {
                lbl_password.text = "Please enter your password"
                lbl_password.setTextColor(Color.parseColor("#FF0000"));
                progressBar.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
                return@setOnClickListener
            } else if (password.length < 5) {
                lbl_password.text = "Please enter a valid password"
                lbl_password.setTextColor(Color.parseColor("#FF0000"));
                progressBar.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
                return@setOnClickListener
            } else {
                lbl_password.text = "Password"
                lbl_password.setTextColor(Color.parseColor("#000000"));
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null)
                    }
                }

        }

        lbl_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

