package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textToLoginPageLink, buttonSignUp;
    private EditText editTextSignUpUsername,editTextSignUpEmail,editTextSignUpMatricID,editTextSignUpPassword;
    private CheckBox checkBoxTerms;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        textToLoginPageLink = (TextView) findViewById(R.id.textToLoginPageLink);
        textToLoginPageLink.setOnClickListener(this);

        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(this);

        editTextSignUpUsername = (EditText) findViewById(R.id.editTextSignUpUsername);
        editTextSignUpEmail = (EditText) findViewById(R.id.editTextSignUpEmail);
        editTextSignUpMatricID = (EditText) findViewById(R.id.editTextSignUpMatricID);
        editTextSignUpPassword = (EditText) findViewById(R.id.editTextSignUpPassword);

        checkBoxTerms = (CheckBox) findViewById(R.id.checkBoxTerms);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textToLoginPageLink:
                this.goToLogin(view);
                break;
            case R.id.buttonSignUp:
                this.SignUpUser();
                break;
        }
    }

    private void SignUpUser() {
        Boolean okay= true;
        String username = editTextSignUpUsername.getText().toString().trim();
        String email = editTextSignUpEmail.getText().toString().trim();
        String matricID = editTextSignUpMatricID.getText().toString().trim();
        String password = editTextSignUpPassword.getText().toString().trim();

        if (username.isEmpty()) {
            editTextSignUpUsername.setError("Username is required");
            editTextSignUpUsername.requestFocus();
            okay = false;
            return;
        }

        if (matricID.isEmpty()) {
            editTextSignUpMatricID.setError("matricID is required");
            editTextSignUpMatricID.requestFocus();
            okay = false;
            return;
        }

        if (!isMatricIDValid(matricID)) {
            editTextSignUpMatricID.setError("MatricID is invalid. Please enter a valid matriciD");
            editTextSignUpMatricID.requestFocus();
            okay = false;
            return;
        }

        if (email.isEmpty()) {
            editTextSignUpEmail.setError("email is required");
            editTextSignUpEmail.requestFocus();
            okay = false;
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextSignUpEmail.setError("Email is nt valid. Please enter a valid email");
            editTextSignUpEmail.requestFocus();
            okay = false;
            return;
        }

        if (password.isEmpty()) {
            editTextSignUpPassword.setError("password is required");
            editTextSignUpPassword.requestFocus();
            okay = false;
            return;
        }

        if (password.length() < 6) {
            editTextSignUpPassword.setError("Min password length should be 6 characters");
            editTextSignUpPassword.requestFocus();
            okay = false;
            return;
        }

        if (!checkBoxTerms.isChecked()){
            Toast.makeText(SignUpActivity.this, "Please agree to the Terms and Conditions", Toast.LENGTH_SHORT).show();
            okay = false;
            return;
        }

        if (okay){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
//                        User user = new User(username,email,matricID);
                        Toast.makeText(SignUpActivity.this, "user has been registered successfully", Toast.LENGTH_SHORT).show();
//                        FirebaseDatabase.getInstance("https://fir-authall-61be6-default-rtdb.firebaseio.com/").getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()){
//                                            Toast.makeText(SignUpActivity.this, "user has been registered successfully", Toast.LENGTH_SHORT).show();
//                                        }
//                                        else {
//                                            Toast.makeText(SignUpActivity.this, "Failed to register!Try again!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });

                    }else {
                        Toast.makeText(SignUpActivity.this, "Failed to register!Try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private boolean isMatricIDValid(String str){
        boolean valid = (str.charAt(0)=='U' || str.charAt(0)=='S' || str.charAt(0)=='u' || str.charAt(0)=='s') && (str.length()==8);
        if(!valid){
            return valid;
        }
        for (int i = 1; i < 8; i++) {
            if(str.charAt(i)<48 || str.charAt(i)>57){
                valid = false;
                return valid;
            }
        }
        return valid;
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}