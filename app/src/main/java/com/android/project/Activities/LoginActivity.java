package com.android.project.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.android.project.Animation;
import com.android.project.R;
import com.android.project.Entities.User;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference mDb;
    private String UserKey="User";
    private List<User> users;
    private User User = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDb = FirebaseDatabase.getInstance().getReference(UserKey);
        auth = FirebaseAuth.getInstance();
        auth.signOut();

        users = new ArrayList<>();

        mDb.get().addOnCompleteListener(task ->
        {
            //список текущих знач в одном каталоге
            for (DataSnapshot ds: task.getResult().getChildren())
            {
                users.add(ds.getValue(com.android.project.Entities.User.class));
            }
        });


        EditText email = findViewById(R.id.login);
        EditText password = findViewById(R.id.password);
        TextView loginButton = findViewById(R.id.loginButton);
        TextView registration = findViewById(R.id.registration);

        loginButton.setOnClickListener(view ->
        {
            if (!email.getText().toString().equals("") && !password.getText().toString().equals(""))
            {

                for(User user: users)
                {
                    if (user.getEmail().equals(email.getText().toString()))
                    {
                        User = user;
                    }
                }
                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(this, "Username: " + User.getLogin() + "\nEmail: " + User.getEmail(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("login", User.getLogin());
                        intent.putExtra("isAdmin", User.isAdmin());
                        intent.putExtra("isInBlackList", User.isInBlacklist());
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Не удалось войти", Toast.LENGTH_LONG).show();
                        Animation.Animate(view, view.getSolidColor(), Color.RED);
                    }
                });
            }
        });

        registration.setOnClickListener(view ->
        {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });

    }

}