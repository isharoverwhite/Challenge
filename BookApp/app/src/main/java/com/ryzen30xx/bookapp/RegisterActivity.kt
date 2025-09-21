package com.ryzen30xx.bookapp

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {
    var email : EditText? = null
    var password : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

    }

}