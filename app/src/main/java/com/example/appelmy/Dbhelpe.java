package com.example.appelmy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelpe extends SQLiteOpenHelper {
    public Dbhelpe(@Nullable Context context) {
        super(context, ContactDB.DATABASE, null, ContactDB.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactDB.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS "+ContactDB.TABLE);
            onCreate(db);
    }

    public long insertContact(String image, String nom, String phone, String email, String note ){
        //ecrire dans la dans la base de donnee
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values =  new ContentValues();
        values.put(ContactDB.IMAGE,image);
        values.put(ContactDB.NOM,nom);
        values.put(ContactDB.TELEPONE,phone);
        values.put(ContactDB.EMAIL,email);
        values.put(ContactDB.NOTE,note);

        //insertion dans la base de donnees
         long id = db.insert(ContactDB.TABLE, null, values);
         db.close();
      return id;
    }
}
