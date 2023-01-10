package com.android.project.UserInterface.Admin;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.project.DB;
import com.android.project.R;
import com.android.project.UserAdapter;

import java.util.ArrayList;

public class AdminFragment extends AppCompatActivity {

    RecyclerView recyclerView;

    DB db;
    ArrayList<String> userID, userEmail, userLogin, userPassword;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin);

        recyclerView = findViewById(R.id.users);

        db = new DB(AdminFragment.this);
        userID = new ArrayList<>();
        userEmail = new ArrayList<>();
        userLogin = new ArrayList<>();
        userPassword = new ArrayList<>();

        displayData();

        userAdapter = new UserAdapter(AdminFragment.this, userID, userEmail, userLogin, userPassword);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminFragment.this));
    }

    void displayData()
    {
        Cursor cursor = db.readUsers();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "Missing data", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext())
            {
                userID.add(cursor.getString(0));
                userEmail.add(cursor.getString(1));
                userLogin.add(cursor.getString(2));
                userPassword.add(cursor.getString(3));
            }
        }
    }
}
