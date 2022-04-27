package com.example.team7_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.team7_app.fragment.DocumentsFragment;
import com.example.team7_app.fragment.HomeFragment;
import com.example.team7_app.fragment.RecentlyFragment;
import com.example.team7_app.fragment.SecurityFragment;
import com.example.team7_app.fragment.TrashFragment;
import com.example.team7_app.fragment.UserFragment;
import com.example.team7_app.my_interface.IClickHomeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity  implements IClickHomeListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_USER = 2;
    private static final int FRAGMENT_SECURITY = 3;

    private static final int FRAGMENT_RECENTLY = 5;
    private static final int FRAGMENT_TRASH = 6;

    private int currentFragment = FRAGMENT_HOME;

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    private void openSecurityFragment() {
        if(currentFragment != FRAGMENT_SECURITY) {
            replaceFragment(new SecurityFragment());
            currentFragment = FRAGMENT_SECURITY;
        }
    }

    private void openRecentlyFragment() {
        if(currentFragment != FRAGMENT_RECENTLY) {
            replaceFragment(new RecentlyFragment());
            currentFragment = FRAGMENT_RECENTLY;
        }
    }

    private void openTrashFragment() {
        if(currentFragment != FRAGMENT_TRASH) {
            replaceFragment(new TrashFragment());
            currentFragment = FRAGMENT_TRASH;
        }
    }
    private void clickOpenProfileSheetDialog() {
        View viewProfile = getLayoutInflater().inflate(R.layout.fragment_profile, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(viewProfile);
        bottomSheetDialog.show();

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewProfile.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
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
            // back tren ban phim
            FragmentManager fragmentManager = getSupportFragmentManager();
            if(fragmentManager.getBackStackEntryCount() > 0)
            {
                fragmentManager.popBackStack();
                currentFragment = FRAGMENT_HOME;
            }
            else
            {
                super.onBackPressed();
                // tat ung dung
                //finishAffinity();
                finish();
            }

        }
    }

    @Override
    public void setCurrentFragment(int id) {
        currentFragment= id;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.nav_recently)
        {
            openRecentlyFragment();
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else if(id==R.id.nav_trash)
        {
            openTrashFragment();
            drawerLayout.closeDrawer(Gravity.LEFT);

        }
        else if(id==R.id.nav_information)
        {
            Intent intent = new Intent(HomeActivity.this, InfoActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_logout)
        {
            drawerLayout.closeDrawer(Gravity.LEFT);
            finish();
        }
        return false;
    }
}