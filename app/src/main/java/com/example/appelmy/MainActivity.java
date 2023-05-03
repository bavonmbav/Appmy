package com.example.appelmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton flota;
    private RecyclerView recyclerView;

    private Dbhelpe dbhelpe;

    private AdapterContact contact;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelpe = new Dbhelpe(this);
        //initialise la liste de contacts
        recyclerView = findViewById(R.id.conctats);
        //Cette méthode permet de définir si la taille de la liste d'éléments affichés dans le RecyclerViewest fixe ou non.
        recyclerView.setHasFixedSize(true);

        flota = findViewById(R.id.flota);
        flota.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, AddContact.class);
            startActivity(intent);

        });

        leadata();
    }

    //la redirection
    private void leadata() {
        contact = new AdapterContact(this, dbhelpe.listeContact());
        recyclerView.setAdapter(contact);

    }

    @Override
    protected void onResume() {
        super.onResume();
        leadata();
    }
}