package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editTextPREmail;
    Button buttonPRSubmit;
//    private
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        editTextPREmail = (EditText) findViewById(R.id.editTextPREmail);
        buttonPRSubmit = (Button) findViewById(R.id.buttonPRSubmit);

        auth = FirebaseAuth.getInstance();

        buttonPRSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(ForgotPasswordActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void resetPassword() {
        String email = editTextPREmail.getText().toString().trim();

        if (email.isEmpty()) {
            editTextPREmail.setError("email is required");
            editTextPREmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextPREmail.setError("Email is nt valid. Please enter a valid email");
            editTextPREmail.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset password", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotPasswordActivity.this, "Try again! Something wrong happened", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}