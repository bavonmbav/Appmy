package com.example.appelmy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton flota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flota = findViewById(R.id.flota);
        flota.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, AddContact.class);
            startActivity(intent);

        });
    }
}