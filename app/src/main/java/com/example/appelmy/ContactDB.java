package com.example.appelmy;

public class ContactDB {
    public static final String DATABASE = "CONTACTS";
    public static final int VERSION = 1;
    public static final String TABLE = "USER_CONTACT";

    //les colonnes

    public static final String ID = "ID";
    public static final String IMAGE = "IMAGES";
    public static final String NOM = "NOMS";
    public static final String TELEPONE = "PHONE";
    public static final String EMAIL = "EMAILS";
    public static final String NOTE = "NOTES";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE +"("
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +IMAGE+" TEXT,"
            +NOM+" TEXT,"
            +TELEPONE+" TEXT,"
            +EMAIL+" TEXT,"
            +NOTE+" TEXT"
            +");";
}
