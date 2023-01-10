package com.android.project.UserInterface.ContentProvider;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.project.R;

public class ViewActivity extends AppCompatActivity {

    TextView textViewForView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        textViewForView = findViewById(R.id.text_view_for_view);

        textViewForView.setText(getIntent().getStringExtra("view"));
    }
}
