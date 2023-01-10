package com.android.project.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.project.Entities.User;
import com.android.project.R;
import com.android.project.databinding.ActivityMainBinding;
import com.android.project.databinding.AppBarMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private boolean isAdmin;
    private String UserKey="User";
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras()!=null)
        {
            isAdmin = getIntent().getExtras().getBoolean("isAdmin");
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        AppBarMainBinding appBar = binding.appBarMain;
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        Menu menu = navigationView.getMenu();

        //если админ - тулбар будет виден, иначе - нет
        if (!isAdmin)
        {
            appBar.toolbar.setVisibility(View.INVISIBLE);
            getSupportActionBar().hide();
            menu.findItem(R.id.nav_admin).setVisible(false);
        }
        else
        {
            appBar.toolbar.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
            menu.findItem(R.id.nav_admin).setVisible(true);
        }

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_admin, R.id.nav_cp)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}