package com.ryzen30xx.chartreux;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailTextView, passwordTextView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        progressbar = findViewById(R.id.login_progress);
        emailTextView = findViewById(R.id.textfield_email);
        passwordTextView = findViewById(R.id.textfield_password);

        TextView register = (TextView) findViewById(R.id.btn_register);
        Button login_btn = (Button) findViewById(R.id.btn_login);

        register.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();

        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                login_btn.setVisibility(View.GONE);
                String email, password;
                email = String.valueOf(emailTextView.getText());
                password = String.valueOf(passwordTextView.getText());

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email must not be empty", Toast.LENGTH_LONG).show();
                    emailTextView.setHint("Email must not be empty");
                    emailTextView.setHintTextColor(Color.RED);
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Password must not be empty", Toast.LENGTH_LONG).show();
                    passwordTextView.setHint("Password must not be empty");
                    passwordTextView.setHintTextColor(Color.RED);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbar.setVisibility(View.GONE);
                                login_btn.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Authentication success.",
                                            LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}