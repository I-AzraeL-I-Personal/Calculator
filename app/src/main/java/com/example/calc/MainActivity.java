package com.example.calc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnClose).setOnClickListener(v -> finish());
        findViewById(R.id.btnAbout).setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("About")
                .setMessage("Calculator\n" +
                        "Author: AzraeL\n\n" +
                        "This is a simple calculator.\n" +
                        "Version: 1.0\n")
                .setPositiveButton("OK", null)
                .create()
                .show());
        findViewById(R.id.btnSimple).setOnClickListener(v ->
                startActivity(new Intent(this, SimpleActivity.class)));
        findViewById(R.id.btnAdvanced).setOnClickListener(v ->
                startActivity(new Intent(this, AdvancedActivity.class)));
    }
}