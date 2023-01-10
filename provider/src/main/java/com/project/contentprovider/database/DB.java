package com.project.contentprovider.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Bands.db";
    private static final int DATABASE_VERSION = 1;

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS CITY_TABLE ( "
                + "IDCITY INTEGER PRIMARY KEY, "
                + "CITYNAME TEXT UNIQUE);"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS BAND_TABLE ("
                + "IDBAND INTEGER PRIMARY KEY, "
                + "CITYNAME TEXT NOT NULL, "
                + "NUMBEROFMEMBERS INTEGER DEFAULT(1), "
                + "BANDNAME TEXT NOT NULL, "
                + "NUMBEROFRELEASES INTEGER DEFAULT(0), "
                + "FOREIGN KEY(CITYNAME) REFERENCES CITY_TABLE(CITYNAME) "
                + "ON DELETE CASCADE ON UPDATE CASCADE);"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS MEMBER_TABLE ("
                + "IDBAND INTEGER NOT NULL, "
                + "IDMEMBER INTEGER PRIMARY KEY, "
                + "MEMBERNAME TEXT NOT NULL, "
                + "BIRTHDATE DATE NOT NULL, "
                + "FOREIGN KEY(IDBAND) REFERENCES BAND_TABLE(IDBAND) "
                + "ON DELETE CASCADE ON UPDATE CASCADE);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE CITY_TABLE;");
        db.execSQL("DROP TABLE BAND_TABLE");
        db.execSQL("DROP TABLE MEMBER_TABLE;");
        onCreate(db);
    }


    public void addCity(SQLiteDatabase db, int idCity, String cityName) {
        ContentValues cv = new ContentValues();
        cv.put("IDCITY", idCity);
        cv.put("CITYNAME", cityName);
        db.insert("CITY_TABLE", null, cv);
        cv.clear();
    }

    public void addBand(SQLiteDatabase db, int idBand, String cityName, int nom, String bandName, int nor) {
        ContentValues cv = new ContentValues();
        cv.put("IDBAND", idBand);
        cv.put("CITYNAME", cityName);
        cv.put("NUMBEROFMEMBERS", nom);
        cv.put("BANDNAME", bandName);
        cv.put("NUMBEROFRELEASES", nor);
        db.insert("BAND_TABLE", null, cv);
        cv.clear();
    }

    public void addMember(SQLiteDatabase db, int idMember, int idBand, String memberName, String birthDate) {
        ContentValues cv = new ContentValues();
        cv.put("IDMEMBER", idMember);
        cv.put("IDBAND", idBand);
        cv.put("MEMBERNAME", memberName);
        cv.put("BIRTHDATE", birthDate);
        db.insert("MEMBER_TABLE", null, cv);
        cv.clear();
    }

    public void initDatabase(SQLiteDatabase db) {
        addCity(db, 1,"Брест");
        addCity(db, 2,"Витебск");
        addCity(db, 3,"Гомель");
        addCity(db, 4,"Гродно");
        addCity(db, 5,"Минск");
        addCity(db, 6,"Могилёв");

        addBand(db, 1,"Брест", 1, "Группа1", 1);
        addBand(db, 2,"Витебск", 2, "Группа2", 2);
        addBand(db, 3,"Гомель", 3, "Группа3", 3);
        addBand(db, 4,"Гродно", 4, "Группа4", 4);
        addBand(db, 5,"Минск", 5, "Группа5", 5);
        addBand(db, 6,"Могилёв", 6, "Группа6", 6);

        addMember(db, 1, 1, "Name1", "01.01.2001");
        addMember(db, 2, 2, "Name2", "01.02.2002");
        addMember(db, 3, 2, "Name3", "02.02.2002");
        addMember(db, 4, 3, "Name4", "01.03.2003");
        addMember(db, 5, 3, "Name5", "02.03.2003");
        addMember(db, 6, 3, "Name6", "03.03.2003");
        addMember(db, 7, 4, "Name7", "01.04.2004");
        addMember(db, 8, 4, "Name8", "02.04.2004");
        addMember(db, 9, 4, "Name9", "03.04.2004");
        addMember(db, 10, 4, "Name10", "04.04.2004");
        addMember(db, 11, 5, "Name11", "01.05.2005");
        addMember(db, 12, 5, "Name12", "02.05.2005");
        addMember(db, 13, 5, "Name13", "03.05.2005");
        addMember(db, 14, 5, "Name14", "04.05.2005");
        addMember(db, 15, 5, "Name15", "05.05.2005");
        addMember(db, 16, 6, "Name16", "01.06.2006");
        addMember(db, 17, 6, "Name17", "02.06.2006");
        addMember(db, 18, 6, "Name18", "03.06.2006");
        addMember(db, 19, 6, "Name19", "04.06.2006");
        addMember(db, 20, 6, "Name20", "05.06.2006");
        addMember(db, 21, 6, "Name21", "06.06.2006");
    }
}