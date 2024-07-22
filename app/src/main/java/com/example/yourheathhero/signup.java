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

public class signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText;
    private TextInputLayout emailInputLayout, passwordInputLayout, confirmPasswordInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.signup_email);
        passwordEditText = findViewById(R.id.signup_pass);
        confirmPasswordEditText = findViewById(R.id.confirm_pass);
        emailInputLayout = findViewById(R.id.signup_email_layout);
        passwordInputLayout = findViewById(R.id.signup_pass_layout);

        findViewById(R.id.signup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        findViewById(R.id.already_have_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this, login.class));
                finish();
            }
        });
    }

    private void signUpUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

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

        if (!password.equals(confirmPassword)) {
            confirmPasswordInputLayout.setError("Passwords do not match");
            return;
        } else {
            confirmPasswordInputLayout.setError(null);
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(signup.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signup.this, HomeActivity.class));  // Replace HomeActivity with your target activity
                    finish();
                } else {
                    Toast.makeText(signup.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
