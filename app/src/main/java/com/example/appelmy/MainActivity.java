package com.example.appelmy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);
        //get search item from menu
        MenuItem item = menu.findItem(R.id.searchContact);
        //search area
        SearchView searchView = (SearchView) item.getActionView();
        //set max value for width
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchContact(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchContact(newText);
                return true;
            }
        });

        return true;
    }
    //rechercher
    private void searchContact(String query) {
        contact = new AdapterContact(this,dbhelpe.getSearchContact(query));
        recyclerView.setAdapter(contact);
    }
//  supprimer toutes la liste
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAllContact:
                dbhelpe.deleteAllContact();
                onResume();
                break;
        }
        return true;
    }
}