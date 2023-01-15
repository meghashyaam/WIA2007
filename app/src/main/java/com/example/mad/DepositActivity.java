package com.example.mad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DepositActivity extends AppCompatActivity {

    Button buttonDepositBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

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

    public void goToBookingComplete(View view) {
        Intent intent = new Intent(DepositActivity.this, BookingCompleteActivity.class);
        startActivity(intent);
        buttonDepositBook = findViewById(R.id.buttonDepositBook);
        final MediaPlayer cheeringSound = MediaPlayer.create(this,R.raw.cheeringsound);
        cheeringSound.start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}