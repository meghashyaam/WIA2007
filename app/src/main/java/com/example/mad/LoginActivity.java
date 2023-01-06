package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //this method is buggy, pls try other workaround like setOnFocusChangeListener
        TextView editLoginUsername = findViewById(R.id.editLoginUsername);
        editLoginUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editLoginUsername.setSelected(!editLoginUsername.isSelected());
            }
        });

        TextView editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        editTextLoginPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextLoginPassword.setSelected(!editTextLoginPassword.isSelected());
            }
        });


    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goToForgotPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }


}