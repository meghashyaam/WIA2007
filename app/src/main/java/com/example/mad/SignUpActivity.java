package com.example.mad;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    com.google.android.gms.common.SignInButton sign_in_button;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private TextView textToLoginPageLink, buttonSignUp;
    private EditText editTextSignUpUsername,editTextSignUpEmail,editTextSignUpMatricID,editTextSignUpPassword;
    private CheckBox checkBoxTerms;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private DatabaseReference mDatabase;

    String username;
    String email;
    String matricID;
    String password;

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textToLoginPageLink:
                this.goToLogin(view);
                break;
            case R.id.buttonSignUp:
                boolean status = this.SignUpUser();
                if (status){

                    ProfileUser prof = new ProfileUser(username,email,matricID,password);
                    mDatabase.child("prof").child(prof.getUsername()).setValue(prof).addOnSuccessListener(suc->{
                        Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(er->{
                        Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                    });;
                }
                break;
        }
    }

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

        mDatabase = FirebaseDatabase.getInstance("https://heyehye-13e9f-default-rtdb.firebaseio.com/").getReference();

        editTextSignUpUsername = (EditText) findViewById(R.id.editTextSignUpUsername);
        editTextSignUpEmail = (EditText) findViewById(R.id.editTextSignUpEmail);
        editTextSignUpMatricID = (EditText) findViewById(R.id.editTextSignUpMatricID);
        editTextSignUpPassword = (EditText) findViewById(R.id.editTextSignUpPassword);

        checkBoxTerms = (CheckBox) findViewById(R.id.checkBoxTerms);

        sign_in_button = findViewById(R.id.sign_in_button);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void SignIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                HomeActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private boolean SignUpUser() {
        Boolean okay = true;
        username = editTextSignUpUsername.getText().toString().trim();
        email = editTextSignUpEmail.getText().toString().trim();
        matricID = editTextSignUpMatricID.getText().toString().trim();
        password = editTextSignUpPassword.getText().toString().trim();

        if (username.isEmpty()) {
            editTextSignUpUsername.setError("Username is required");
            editTextSignUpUsername.requestFocus();
            okay = false;
            return okay;
        }

        if(username.contains("@"))

        if (matricID.isEmpty()) {
            editTextSignUpMatricID.setError("matricID is required");
            editTextSignUpMatricID.requestFocus();
            okay = false;
            return okay;
        }

        if (!isMatricIDValid(matricID)) {
            editTextSignUpMatricID.setError("MatricID is invalid. Please enter a valid matriciD");
            editTextSignUpMatricID.requestFocus();
            okay = false;
            return okay;
        }

        if (email.isEmpty()) {
            editTextSignUpEmail.setError("email is required");
            editTextSignUpEmail.requestFocus();
            okay = false;
            return okay;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextSignUpEmail.setError("Email is nt valid. Please enter a valid email");
            editTextSignUpEmail.requestFocus();
            okay = false;
            return okay;
        }

        if (password.isEmpty()) {
            editTextSignUpPassword.setError("password is required");
            editTextSignUpPassword.requestFocus();
            okay = false;
            return okay;
        }

        if (password.length() < 6) {
            editTextSignUpPassword.setError("Min password length should be 6 characters");
            editTextSignUpPassword.requestFocus();
            okay = false;
            return okay;
        }

        if (!checkBoxTerms.isChecked()){
            Toast.makeText(SignUpActivity.this, "Please agree to the Terms and Conditions", Toast.LENGTH_SHORT).show();
            okay = false;
            return okay;
        }
        return okay;

    }

//    public void writeNewUser(){
//
//    }

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
}