package com.ryzen30xx.chartreux;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText emailTextView, passwordTextView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        progressbar = findViewById(R.id.register_progress);
        emailTextView = findViewById(R.id.textfield_email);
        passwordTextView = findViewById(R.id.textfield_password);
        Button register_btn = (Button) findViewById(R.id.btn_registerNow);


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                register_btn.setVisibility(View.GONE);
                String email, password;
                email = String.valueOf(emailTextView.getText());
                password = String.valueOf(passwordTextView.getText());

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email must not be empty", Toast.LENGTH_LONG).show();
                    emailTextView.setHint("Email must not be empty");
                    emailTextView.setHintTextColor(Color.RED);
                    progressbar.setVisibility(View.GONE);
                    register_btn.setVisibility(View.VISIBLE);
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Password must not be empty", Toast.LENGTH_LONG).show();
                    passwordTextView.setHint("Password must not be empty");
                    passwordTextView.setHintTextColor(Color.RED);
                    progressbar.setVisibility(View.GONE);
                    register_btn.setVisibility(View.VISIBLE);
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Register Failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }


}
