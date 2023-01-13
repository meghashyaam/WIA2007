package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

//    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

    public void goToReportDamage(View view) {
        Intent intent = new Intent(SettingsActivity.this, ReportDamageActivity.class);
        startActivity(intent);
    }

    public void goAppFeedback(View view) {
        Intent intent = new Intent(SettingsActivity.this, AppFeedbackActivity.class);
        startActivity(intent);
    }

    public void goToLogout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
