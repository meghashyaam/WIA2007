package com.example.mad;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment{

    TextView textName;

    TextView kk;
    TextView cat;
    TextView date;
    TextView time;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private FirebaseAuth.AuthStateListener authStateListener;

    public ProfileUser generalProfile;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        textName =(TextView) v.findViewById(R.id.textName);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(getActivity(),gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

//        if (account!=null) {
//            String Name = account.getDisplayName();
//            textName.setText(Name);
//        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            DocumentReference docRef = db.collection("Login").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()){
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Map<String, Object> newData = document.getData();
                            String profName = (String) newData.get("username");
                            generalProfile = new ProfileUser((String) newData.get("username"), (String) newData.get("email"), (String) newData.get("matricID"), (String) newData.get("password"));
                            textName.setText(generalProfile.getUsername());
                        }
                    }
                }
            });
        }



        kk = (TextView) v.findViewById(R.id.TVBookLocation);
        cat = (TextView) v.findViewById(R.id.TVBookSpace);
        date = (TextView) v.findViewById(R.id.TVBookDate);
        time = (TextView) v.findViewById(R.id.TVBookTime);

        String bookcat = getActivity().getIntent().getStringExtra("cat");
        String bookloc = getActivity().getIntent().getStringExtra("kk");
        String bookdate = getActivity().getIntent().getStringExtra("date");
        String booktime = getActivity().getIntent().getStringExtra("time");

        cat.setText(bookcat);
        kk.setText(bookloc);
        date.setText(bookdate);
        time.setText(booktime);








//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//            }
//        };
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

}