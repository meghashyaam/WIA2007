package com.example.mad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookingCompleteActivity extends AppCompatActivity {

    TextView kk;
    TextView cat;
    TextView date;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_complete);



        kk = (TextView) findViewById(R.id.TVBookLocation);
        cat = (TextView) findViewById(R.id.TVBookSpace);
        date = (TextView) findViewById(R.id.TVBookDate);
        time = (TextView) findViewById(R.id.TVBookTime);

        String bookcat = getIntent().getStringExtra("cat");
        String bookloc = getIntent().getStringExtra("kk");
        String bookdate = getIntent().getStringExtra("date");
        String booktime = getIntent().getStringExtra("time");

        cat.setText(bookcat);
        kk.setText(bookloc);
        date.setText(bookdate);
        time.setText(booktime);




    }



    public void goToHome(View view) {
        Intent intent = new Intent(BookingCompleteActivity.this, MainActivity.class);
        startActivity(intent);
    }
}