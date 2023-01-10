package com.android.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

public class DB extends SQLiteOpenHelper {
    private static final String DB_NAME = "LocalDB.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Users";

    private static final String ID_COL = "ID";
    private static final String EMAIL_COL = "Email";
    private static final String LOGIN_COL = "Login";
    private static final String PASSWORD_COL = "Password";


    public DB(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //создание
    @Override
    public void onCreate(SQLiteDatabase db)
    {
       String query = "CREATE TABLE " + TABLE_NAME +
                " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EMAIL_COL + " TEXT," +
                LOGIN_COL + " TEXT," +
                PASSWORD_COL + " TEXT);";
       db.execSQL(query);
    }

    //обновление (если таблица существует - она удалится и пересоздатся)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldT, int newT)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String email, String login, String password)
    {
        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EMAIL_COL, email);
        cv.put(LOGIN_COL, login);
        cv.put(PASSWORD_COL, password);
        sdb.insert(TABLE_NAME, null, cv);
    }

    public Cursor readUsers()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
