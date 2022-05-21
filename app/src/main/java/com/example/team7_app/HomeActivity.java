package com.example.team7_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.team7_app.Model.User;
import com.example.team7_app.fragment.HomeFragment;
import com.example.team7_app.fragment.RecentlyFragment;
import com.example.team7_app.fragment.TrashFragment;
import com.example.team7_app.my_interface.IClickHomeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class HomeActivity extends AppCompatActivity  implements IClickHomeListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 1;

    private static final int FRAGMENT_RECENTLY = 5;
    private static final int FRAGMENT_TRASH = 6;

    private int currentFragment = FRAGMENT_HOME;
    private User usr;

    public String getUsername() {
        return mUsername;
    }

    private String mUsername="";
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

        //login
        Bundle bundleRcvUser= getIntent().getExtras();
        if( bundleRcvUser != null)
        {
            usr = (User) bundleRcvUser.get("object_user");
            if(usr != null)
            {
                mUsername= usr.getLogin().trim();
            }
        }

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
                        else {
                            openHomeFragment();
                        }
                        break;

                    case R.id.bottom_bar_user:
                        clickOpenProfileSheetDialog();
                        break;

                }
                return true;

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        if (checkPermission()) {
            replaceFragment(new HomeFragment(), "Home");
            bottomNavigationView.setSelectedItemId(R.id.bottom_bar_home);
        }
        else {
            requestPermission();
        }
    }

    private void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void openHomeFragment() {
        if(currentFragment != FRAGMENT_HOME) {
            if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack("Home", 0);
            }
            else {
                replaceFragment(new HomeFragment(), "Home");
            }
            currentFragment = FRAGMENT_HOME;
        }
    }

    private void openRecentlyFragment() {
        if(currentFragment != FRAGMENT_RECENTLY) {
            replaceFragment(new RecentlyFragment(), "Recently");
            currentFragment = FRAGMENT_RECENTLY;
        }
    }

    private void openTrashFragment() {
        if(currentFragment != FRAGMENT_TRASH) {
            replaceFragment(new TrashFragment(), "Trash");
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

    private void replaceFragment(Fragment fragment, String nameFrag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(nameFrag);
        transaction.commit();
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", this.getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        }
        else {
            Dexter.withContext(this).withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            replaceFragment(new HomeFragment(), "Home");
                            bottomNavigationView.setSelectedItemId(R.id.bottom_bar_home);
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
        }
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else {
            // back tren ban phim
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (getSupportFragmentManager().getBackStackEntryCount() == 1){
                finish();
                // tat ung dung
                //finishAffinity();
            }
            else
            {
                super.onBackPressed();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    replaceFragment(new HomeFragment(), "Home");
                } else {
                    Toast.makeText(this, "Please allow permission for storage access!", Toast.LENGTH_SHORT).show();
                    requestPermission();
                }
            }
        }
    }
}