package com.example.mad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppFeedbackActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference fb = db.collection("Feedback");

    private EditText feeback;
    private RatingBar lol;
    private Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_feedback);

        feeback = findViewById(R.id.ETFeedback);
        lol = findViewById(R.id.ratingBar);
        submit = findViewById(R.id.btnSubmit);

        Toolbar toolbar = findViewById(R.id.materialToolbar);
        TextView toolbartitle = findViewById(R.id.toolbar_title);

        ViewGroup parent = (ViewGroup) toolbartitle.getParent();
        if (parent != null) {
            parent.removeView(toolbartitle);
        }

        toolbar.addView(toolbartitle);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        HashMap<String, Object> loll = new HashMap<>();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feeback1 = feeback.getText().toString();
                int scale = lol.getNumStars();
                loll.put("feedback", feeback1);
                loll.put("scale", scale);
                fb.add(loll);
                Intent intent = new Intent(AppFeedbackActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(AppFeedbackActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void goToHome(View view) {
        Intent intent = new Intent(AppFeedbackActivity.this, MainActivity.class);
        startActivity(intent);
    }
}