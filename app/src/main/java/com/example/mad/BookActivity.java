package com.example.mad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

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

        //change press color for buttonBookStudyCube
        Button buttonBookStudyCube = findViewById(R.id.buttonBookStudyCube);
        buttonBookStudyCube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonBookStudyCube.setSelected(!buttonBookStudyCube.isSelected());
            }
        });

    }

    public void goToPayment(View view) {
        Intent intent = new Intent(BookActivity.this, DepositActivity.class);
        startActivity(intent);
    }





}