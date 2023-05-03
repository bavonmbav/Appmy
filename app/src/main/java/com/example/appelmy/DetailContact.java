package com.example.appelmy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailContact extends AppCompatActivity {

    private ImageView imageView;
    private TextView nom, tel, mail, not;
    private Dbhelpe dbhelpe;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        dbhelpe =new Dbhelpe(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");

        imageView = findViewById(R.id.imagesv);
        nom = findViewById(R.id.detailNom);
        tel = findViewById(R.id.telephone);
        mail = findViewById(R.id.emails);
        not = findViewById(R.id.note);

        dataselect();

    }


    public void dataselect(){

        String select = "SELECT * FROM " + ContactDB.TABLE + " WHERE " + ContactDB.ID + "=\"" + id +"\"";
        SQLiteDatabase db  = dbhelpe.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()){
            do {

                String image = ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.IMAGE));
                String noms = ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.NOM));
                String telp = ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.TELEPONE));
                String mails = ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.EMAIL));
                String nots = ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.NOTE));

                nom.setText(noms);
                tel.setText(telp);
                mail.setText(mails);
                not.setText(nots);

                if (image.equals(""))
                {
                    imageView.setImageResource(R.drawable.baseline_account_circle_24);

                }else {

                    imageView.setImageURI(Uri.parse(image));
                }

            }while (cursor.moveToNext());
        }
        db.close();
    }
}