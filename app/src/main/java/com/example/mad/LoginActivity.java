package com.example.mad;
import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    com.google.android.gms.common.SignInButton sign_in_button;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private EditText editTextLoginEmail,editTextLoginPassword;
    private Button buttonLogin;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseUser FUser = FirebaseAuth.getInstance().getCurrentUser();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference fb = db.collection("Login");

    private FirebaseAuth.AuthStateListener authStateListener;

    public ProfileUser generalProfile;

    String email;
    String password;
    //    @SuppressLint("MissingInflatedId")
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);

        //to stop login

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    if(user.isEmailVerified()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
//                        firebaseAuth.signOut();
                        return;
                    }
                }
            }
        };

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

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

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = userLogin();
                if (!status){
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                            DocumentReference docRef = db.collection("Login").document(mUser.getUid());
                            if (mUser.isEmailVerified()) {
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                Map<String, Object> newData = document.getData();
//                                newData.keySet();
                                                newData.get("email");
                                                ProfileUser prof = new ProfileUser(String.valueOf(newData.get("username")),(String)email,String.valueOf(newData.get("matricID")),(String)password);
                                                fb.document(mAuth.getCurrentUser().getUid()).set(prof);
                                                fb.document((String) newData.get("matricID")).set(prof);
//                                                   username = String.valueOf(newData.get("email"));
                                            } else {
                                                Log.d(TAG, "No such document");
                                                Toast.makeText(LoginActivity.this, "Wrong email! Enter again", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                            Toast.makeText(LoginActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                });
                            } else{
                                Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong credentials! Check your credentials!", Toast.LENGTH_SHORT).show();
                        }
                    };
                });
//                                ProfileUser prof = new ProfileUser(,email, (String) newData.get("matricID"),password);
//                                docRef.set(prof).addOnSuccessListener(suc->{
//                                    Toast.makeText(LoginActivity.this, "Record is inserted", Toast.LENGTH_SHORT).show();
//                                }).addOnFailureListener(er->{
//                                    Toast.makeText(LoginActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
//                                });
//                                goToHome();
//                            }
//                            else{
//                                Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//                            Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    private void goToHome() {
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }
//                });
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                String email;
//                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                                Map<String, Object> newData = document.getData();
////                                newData.keySet();
//                                newData.get("email");
//                                email = String.valueOf(newData.get("email"));
//                                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if(task.isSuccessful()){
//                                            ProfileUser prof = new ProfileUser( email,email, (String) newData.get("matricID"),password);
//                                            docRef.set(prof).addOnSuccessListener(suc->{
//                                                Toast.makeText(LoginActivity.this, "Record is inserted", Toast.LENGTH_SHORT).show();
//                                            }).addOnFailureListener(er->{
//                                                Toast.makeText(LoginActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
//                                            });
//                                            mUser = mAuth.getCurrentUser();
//                                            if (mUser.isEmailVerified()) {
//                                                goToHome();
//                                            }
//                                            else{
////                                                FUser.sendEmailVerification();
//                                                Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }else {
//                                            Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                    private void goToHome() {
//                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        startActivity(intent);
//                                    }
//                                });
////                                Toast.makeText(LoginActivity.this, abc, Toast.LENGTH_SHORT).show();
//                                return;
//                            } else {
//                                Log.d(TAG, "No such document");
//                                Toast.makeText(LoginActivity.this, "Wrong username! Enter again", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        } else {
//                            Log.d(TAG, "get failed with ", task.getException());
//                            Toast.makeText(LoginActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
//                });
            }
        });
    }

    public void Bruthers(){
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("Login").document(mUser.getUid());
        if (mUser.isEmailVerified()) {
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Map<String, Object> newData = document.getData();
//                                newData.keySet();
                            newData.get("email");
                            ProfileUser prof = new ProfileUser(String.valueOf(newData.get("username")),(String)email,String.valueOf(newData.get("matricID")),(String)password);
                            fb.document(mAuth.getCurrentUser().getUid()).set(prof);
                            fb.document((String) newData.get("matricID")).set(prof);
//                                                   username = String.valueOf(newData.get("email"));
                        } else {
                            Log.d(TAG, "No such document");
                            Toast.makeText(LoginActivity.this, "Wrong email! Enter again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                        Toast.makeText(LoginActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
        } else{
            Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    private boolean userLogin() {
        email = editTextLoginEmail.getText().toString().trim();
        password = editTextLoginPassword.getText().toString().trim();

        if ( email.isEmpty()){
            editTextLoginEmail.setError("Username is required!");
            editTextLoginEmail.requestFocus();
            return false;
        }

//        String msg = isUsernameValid( email);
//        if(msg.length()!=0){
//            editTextLoginEmail.setError(msg);
//            editTextLoginEmail.requestFocus();
//            return false;
//        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextLoginEmail.setError("Please enter a valid email");
            editTextLoginEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()){
            editTextLoginPassword.setError("Password is required!");
            editTextLoginPassword.requestFocus();
            return false;
        }

//        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                if(task.isSuccessful()){
//                    FirebaseUser FUser = FirebaseAuth.getInstance().getCurrentUser();
//
//                    if (FUser.isEmailVerified()) {
//                        goToHome();
//                    }
//                    else{
//                        FUser.sendEmailVerification();
//                        Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            private void goToHome() {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
        return true;
    }

    private String isUsernameValid(String username) {
        if (username.length()>20){
            return "Username is too long. Must be 20 characters or less";
        }
        for (int i = 0; i<username.length(); i++) {
            if( (!(username.charAt(i)>=48 && username.charAt(i)<=57))  &&  (!(username.charAt(i)>=65 && username.charAt(i)<=90))
                    &&  (!(username.charAt(i)>=97 && username.charAt(i)<=122)) ){
                return "Username cannot contain characters other than alphabets and numbers";
            }
        }
        return "";
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goToForgotPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
}