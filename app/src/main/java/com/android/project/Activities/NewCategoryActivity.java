package com.android.project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.android.project.JSON;
import com.android.project.R;

public class NewCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {

            List<String> categories = JSON.importFromJSON(getApplicationContext());
            if (categories == null)
                categories = new ArrayList<>();

            EditText newCategory = findViewById(R.id.edit_word);
            if (!newCategory.getText().toString().equals(""))
            {
                categories.add(newCategory.getText().toString());
                JSON.exportToJSON(getApplicationContext(), categories);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_LONG).show();
            }
        });
    }
}