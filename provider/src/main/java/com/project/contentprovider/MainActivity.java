package com.project.contentprovider;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.contentprovider.database.DB;

public class MainActivity extends AppCompatActivity {

    TextView textViewForView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewForView = findViewById(R.id.text_view_for_view);

        textViewForView.setText(getIntent().getStringExtra("view"));
    }
}