package com.example.team7_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.team7_app.fragment.DocumentsFragment;
import com.example.team7_app.fragment.HomeFragment;
import com.example.team7_app.fragment.SecurityFragment;
import com.example.team7_app.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_USER = 2;
    private static final int FRAGMENT_SECURITY = 3;

    private int currentFragment = FRAGMENT_HOME;

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_bar_menu:
                        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            drawerLayout.openDrawer(Gravity.LEFT);
                        }
                        else {
                            drawerLayout.closeDrawer(Gravity.LEFT);
                        }
                        break;
                    case R.id.bottom_bar_home:
                        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            drawerLayout.closeDrawer(Gravity.LEFT);
                        }
                        openHomeFragment();
                        break;

                    case R.id.bottom_bar_user:
                        /*if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            drawerLayout.closeDrawer(Gravity.LEFT);
                        }
                        openUserFragment();*/

                        clickOpenProfileSheetDialog();
                        break;

                    case R.id.bottom_bar_security:
                        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            drawerLayout.closeDrawer(Gravity.LEFT);
                        }
                        openSecurityFragment();
                        break;
                }
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        replaceFragment(new HomeFragment());
        bottomNavigationView.setSelectedItemId(R.id.bottom_bar_home);
    }

    private void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void openHomeFragment() {
        if(currentFragment != FRAGMENT_HOME) {
            replaceFragment(new HomeFragment());
            currentFragment = FRAGMENT_HOME;
        }
    }

    private void openUserFragment() {
        if(currentFragment != FRAGMENT_USER) {
            replaceFragment(new DocumentsFragment());
            currentFragment = FRAGMENT_USER;
        }
    }

    private void clickOpenProfileSheetDialog() {
        View viewProfile = getLayoutInflater().inflate(R.layout.fragment_profile, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewProfile);
        bottomSheetDialog.show();
    }

    private void openSecurityFragment() {
        if(currentFragment != FRAGMENT_SECURITY) {
            replaceFragment(new SecurityFragment());
            currentFragment = FRAGMENT_SECURITY;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else {
            super.onBackPressed();
        }
    }
}