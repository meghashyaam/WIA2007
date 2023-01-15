package com.example.mad;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportDamageActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reports = db.collection("Report");
    private String cat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_damage);


        Button button = findViewById(R.id.buttonBookHalls2);
        Button button1 = findViewById(R.id.buttonBookStudyCube2);
        Button button2 = findViewById(R.id.buttonCourts2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                button1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                cat = "Hall";
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                cat = "Study Cube";
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                button1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                cat = "Court";
            }
        });

        List<String> college = new ArrayList<>();

        college.add("KK 1");
        college.add("KK 2");
        college.add("KK 3");
        college.add("KK 4");
        college.add("KK 5");
        college.add("KK 6");
        college.add("KK 7");
        college.add("KK 8");
        college.add("KK 9");
        college.add("KK 10");
        college.add("KK 11");
        college.add("KK 12");

        Spinner spinner = findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReportDamageActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, college);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        EditText report = findViewById(R.id.ETReport);
        Button submit = findViewById(R.id.btnSubmit2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cat.equals("")){
                    String reported = report.getText().toString();
                    String kk = (String) spinner.getSelectedItem();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("report", reported);
                    hashMap.put("kk", kk);
                    hashMap.put("category", cat);
                    reports.add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Intent intent = new Intent(ReportDamageActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(ReportDamageActivity.this, "Thank you for reporting!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else
                    Toast.makeText(ReportDamageActivity.this, "Please choose category", Toast.LENGTH_SHORT).show();

            }
        });

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
}
}
