package com.example.appelmy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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

    //request insert
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
//        request delete
    public void deleteContact(String id){
        //get writable database
        SQLiteDatabase db =  getWritableDatabase();

        //delete query
        db.delete(ContactDB.TABLE,"WHERE"+" =? ",new String[]{id});

        db.close();
    }
    // delete toutes les donnees de la table
    public void deleteAllContact(){
        //get writable database
        SQLiteDatabase db = getWritableDatabase();

        //query for delete
        db.execSQL("DELETE FROM "+ContactDB.TABLE);
        db.close();
    }

    //lister tous les contact
    public ArrayList<ModelContact> listeContact (){

        ArrayList<ModelContact> liste = new ArrayList<>();
        String select = "SELECT * FROM " + ContactDB.TABLE;
        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst())
        {
            do {
                ModelContact contact = new ModelContact(
                       ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.NOM)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.TELEPONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.NOTE))
                );
                liste.add(contact);
            }while (cursor.moveToNext());
        }
        return liste;
    }


//    rechercher dans la liste de contact
public ArrayList<ModelContact> getSearchContact(String query){

    //  return arraylist of modelContact class
    ArrayList<ModelContact> contactList = new ArrayList<>();

    // get readable database
    SQLiteDatabase db = getReadableDatabase();

    //query for search
    String queryToSearch = "SELECT * FROM "+ContactDB.TABLE+" WHERE "+ContactDB.NOM + " LIKE '%" +query+"%'";

    Cursor cursor = db.rawQuery(queryToSearch,null);

    // looping through all record and add to list
    if (cursor.moveToFirst())
    {
        do {
            ModelContact contact = new ModelContact(
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.ID)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.IMAGE)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.NOM)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.TELEPONE)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.EMAIL)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.NOTE))
            );
            contactList.add(contact);
        }while (cursor.moveToNext());
    }
    db.close();
    return contactList;
    }

    //modification de contact
    public void updateContact(String id,String image,String name,String phone,String email,String note){

        // database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValue class object to save data
        ContentValues contentValues = new ContentValues();

        contentValues.put(ContactDB.IMAGE,image);
        contentValues.put(ContactDB.NOM,name);
        contentValues.put(ContactDB.TELEPONE,phone);
        contentValues.put(ContactDB.EMAIL,email);
        contentValues.put(ContactDB.NOTE,note);


        //update data in row, It will return id of record
        db.update(ContactDB.TABLE,contentValues,ContactDB.ID+" =? ",new String[]{id} );

        // close db
        db.close();

    }

}


