package com.example.mad;
import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    com.google.android.gms.common.SignInButton sign_in_button;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private TextView textToLoginPageLink, buttonSignUp;
    private EditText editTextSignUpUsername,editTextSignUpEmail,editTextSignUpMatricID,editTextSignUpPassword;
    private CheckBox checkBoxTerms;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private FirebaseUser mUser = mAuth.getCurrentUser();;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference fb = db.collection("Login");

    private DatabaseReference wDatabase;
    String[] abs = {""};

//    private DatabaseFirestore rDatabase;

    String username;
    String email;
    String matricID;
    String password;

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textToLoginPageLink:
                this.goToLogin(view);
                break;
//            case R.id.buttonSignUp:
//                boolean status = this.SignUpUser();
//                if (status){
////                    checkPreviousRecord(username);
//                    ProfileUser prof = new ProfileUser(username,email,matricID,password);
//                    fb.document(prof.getUsername()).set(prof).addOnSuccessListener(suc->{
//                        Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
//                    }).addOnFailureListener(er->{
//                        Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
//                    });
//                }
//                break;
        }
    }

    private void checkPreviousRecord(String str) {
        boolean[] bool = {true};
//        final String[] abs = {""};


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


//        String userID = mUser.getUid();

        textToLoginPageLink = (TextView) findViewById(R.id.textToLoginPageLink);
        textToLoginPageLink.setOnClickListener(this);

        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = SignUpUser();
                if (!status){
                    return;
                }
//                    checkPreviousRecord(username);
                fb.document(matricID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Toast.makeText(SignUpActivity.this, "matricID is already in use!", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Log.d(TAG, "No such document");
                                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
//                        User user = new User(username,email,matricID);
                                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                            String UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            Toast.makeText(SignUpActivity.this, "user has been registered successfully", Toast.LENGTH_SHORT).show();
                                            ProfileUser prof = new ProfileUser(username,email,matricID,password);
                                            fb.document(UserId).set(prof).addOnSuccessListener(suc->{
                                                Toast.makeText(SignUpActivity.this, "Record is inserted", Toast.LENGTH_SHORT).show();
                                            }).addOnFailureListener(er->{
                                                Toast.makeText(SignUpActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                            });

                                            fb.document((String)prof.getMatricID()).set(prof).addOnSuccessListener(suc->{
                                                Toast.makeText(SignUpActivity.this, "Record is inserted", Toast.LENGTH_SHORT).show();
                                            }).addOnFailureListener(er->{
                                                Toast.makeText(SignUpActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
//                                            FirebaseAuth.getInstance().signOut();
                                            return;
                                        }else {
                                            Toast.makeText(SignUpActivity.this, "Email id already exists!\\nSelect a different email!", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                });
//                                FirebaseAuth.getInstance().signOut();;
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                            Toast.makeText(SignUpActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
//                goToLogout();
            }

            private void goToLogout() {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        })
        ;

        wDatabase = FirebaseDatabase.getInstance().getReference();

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
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void goToLogout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
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
        boolean okay = true;
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
        String msg = isUsernameValid(username);
        if(msg.length()!=0){
            editTextSignUpUsername.setError(msg);
            editTextSignUpUsername.requestFocus();
            okay = false;
            return okay;
        }

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