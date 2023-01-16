package com.example.mad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reports = db.collection("Book");
    private String cat = "";
    private String time = "";
    private String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // button for categories

        Button button = findViewById(R.id.buttonBookHalls3);
        Button button1 = findViewById(R.id.buttonBookStudyCube);
        Button button2 = findViewById(R.id.buttonCourts);

        // Set initial button colour
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
        button1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
        button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));

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

        // display date

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_YEAR,1);
        String nextDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.textDate);
        TextView textViewDate2 = findViewById(R.id.textDate2);
        textViewDate.setText(currentDate);
        textViewDate2.setText(nextDate);

        // Set initial button colour
        textViewDate.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
        textViewDate2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDate.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                textViewDate2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                date = textViewDate.getText().toString();
            }
        });

        textViewDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDate2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                textViewDate.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                date = textViewDate2.getText().toString();
            }
        });


        // button for time

        Button button3 = findViewById(R.id.buttonTime34);
        Button button4 = findViewById(R.id.buttonTime45);
        Button button5 = findViewById(R.id.buttonTime56);
        Button button6 = findViewById(R.id.buttonTime67);
        Button button7 = findViewById(R.id.buttonTime78);
        Button button8 = findViewById(R.id.buttonTime89);

        // Set initial button colour
        button8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
        button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
        button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
        button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
        button7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
        button6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                time = "3pm-4pm";
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                time = "4pm-5pm";
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                time = "5pm-6pm";
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                time = "6pm-7pm";
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                time = "7pm-8pm";
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bdff22")));
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                button6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22000000")));
                time = "8pm-9pm";
            }
        });

        List<String> college = new ArrayList<>();

        college.add("Kolej Kediaman 1");
        college.add("Kolej Kediaman 2");
        college.add("Kolej Kediaman 3");
        college.add("Kolej Kediaman 4");
        college.add("Kolej Kediaman 5");
        college.add("Kolej Kediaman 6");
        college.add("Kolej Kediaman 7");
        college.add("Kolej Kediaman 8");
        college.add("Kolej Kediaman 9");
        college.add("Kolej Kediaman 10");
        college.add("Kolej Kediaman 11");
        college.add("Kolej Kediaman 12");

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BookActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, college);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        EditText request = findViewById(R.id.editTextBookRequest);
        Button book = findViewById(R.id.buttonPay);
        //TextView datee = findViewById(R.id.textDate);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cat.equals("")) {
                    String requestStuff = request.getText().toString();
                    String kk = (String) spinner.getSelectedItem();
                    //String datePicked = datee.getText().toString();

                    HashMap<String, Object> hashMap1 = new HashMap<>();

                    hashMap1.put("kk", kk);
                    hashMap1.put("category", cat);
                    hashMap1.put("date", date);
                    hashMap1.put("time", time);
                    hashMap1.put("request", requestStuff);

                    reports.add(hashMap1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(BookActivity.this, "Booking Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(BookActivity.this, MainActivity.class);

                            intent.putExtra("cat", cat);
                            intent.putExtra("kk", kk);
                            intent.putExtra("date", date);
                            intent.putExtra("time", time);

                            startActivity(intent);
                            playSound();





                        }
                    });
                } else
                    Toast.makeText(BookActivity.this, "Please complete details", Toast.LENGTH_SHORT).show();

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

    public void goToPayment(View view) {
        Intent intent = new Intent(BookActivity.this, DepositActivity.class);
        startActivity(intent);
    }

    public void playSound(){
        final MediaPlayer cheeringSound = MediaPlayer.create(this, R.raw.cheeringsound);
        cheeringSound.start();
    }

}