package com.project.contentprovider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CPActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String AUTHORITY_BAND = "provider.BandsList";
    public static final String AUTHORITY_MEMBER = "provider.MembersList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp);

        findViewById(R.id.get_bands).setOnClickListener(this);
        findViewById(R.id.get_members).setOnClickListener(this);
    }


    public String getBands() {
        String string = "";
        Uri uri = Uri.parse("content://" + AUTHORITY_BAND + "/bands");

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                string += "\tID группы: " + cursor.getInt(0) + "\n" +
                        "\tГород: " + cursor.getString(1) + "\n" +
                        "\tКол-во участников: " + cursor.getString(2) + "\n" +
                        "\tНазвание группы: " + cursor.getString(3) + "\n" +
                        "\tКол-во релизов: " + cursor.getString(4) + "\n\n";
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return string;
    }

    public String getMembers() {
        String string = "";
        Uri uri = Uri.parse("content://" + AUTHORITY_MEMBER + "/members");

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                string += "\tID группы: " + cursor.getInt(0) + "\n" +
                        "\tID участника: " + cursor.getString(1) + "\n" +
                        "\tИмя: " + cursor.getString(2) + "\n" +
                        "\tДата рождения: " + cursor.getString(3) + "\n\n";
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return string;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_bands:
                Intent intentGroup = new Intent(CPActivity.this, MainActivity.class);
                intentGroup.putExtra("view", getBands());
                startActivity(intentGroup);
                break;
            case R.id.get_members:
                Intent intentStudent = new Intent(CPActivity.this, MainActivity.class);
                intentStudent.putExtra("view", getMembers());
                startActivity(intentStudent);
                break;
        }
    }
}