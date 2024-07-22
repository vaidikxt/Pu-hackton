package com.example.yourheathhero;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText emailEditText, passwordEditText;
    private TextInputLayout emailInputLayout, passwordInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.email_input_edittext);
        passwordEditText = findViewById(R.id.password_input_edittext);
        emailInputLayout = findViewById(R.id.email_input_layout);
        passwordInputLayout = findViewById(R.id.password_input_layout);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });

        findViewById(R.id.signup_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, signup.class));
            }
        });
    }

    private void signInUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!isValidEmail(email)) {
            emailInputLayout.setError("Invalid email address");
            return;
        } else {
            emailInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            passwordInputLayout.setError("Password must be at least 6 characters");
            return;
        } else {
            passwordInputLayout.setError(null);
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(login.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(login.this, HomeActivity.class));  // Replace HomeActivity with your target activity
                    finish();
                } else {
                    Toast.makeText(login.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
