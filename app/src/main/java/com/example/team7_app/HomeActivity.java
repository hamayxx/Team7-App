package com.example.team7_app;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.team7_app.API.APIService;
import com.example.team7_app.API.ServiceGenerator;
import com.example.team7_app.Model.ChangePass;
import com.example.team7_app.Model.ChangePassResponse;
import com.example.team7_app.Model.UpdateUserDTO;
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

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity  implements IClickHomeListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 1;

    private static final int FRAGMENT_RECENTLY = 5;
    private static final int FRAGMENT_TRASH = 6;
    private final static String TAG = "TEAM8_DEBUGGER";

    private int currentFragment = FRAGMENT_HOME;
    private User usr;

    private String mUsername, mBirth;
    private String mEmail, mGender;
    private String mToken;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;

    public String getUsername() {
        return mUsername;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //getSupportActionBar().hide();
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
                mUsername = usr.getLogin().trim();
                mEmail = usr.getEmail().trim();
                mToken = usr.getToken().trim();
                mGender = "";
                mBirth = "";

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

    //pop-up logout
    private void openLogoutDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_logout);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.CENTER  == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        dialog.show();

        TextView tvYes = dialog.findViewById(R.id.dl_popup_logout_yes);
        TextView tvCancel = dialog.findViewById(R.id.dl_popup_logout_cancel);

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    // change pass
    private void clickOpenPassSheetDialog(UpdateUserDTO updateUserDTO) {
        View viewProfile = getLayoutInflater().inflate(R.layout.fragment_profile_pass, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(viewProfile);
        bottomSheetDialog.show();

        TextView tvUsername = (TextView) viewProfile.findViewById(R.id.fm_profile_pass_et_pass);

        CardView btnNext = viewProfile.findViewById(R.id.fm_profile_pass_btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = tvUsername.getText().toString();

                if (!oldPass.equals(updateUserDTO.getPassword())) {
                    ChangePass changePass = new ChangePass(oldPass, updateUserDTO.getPassword());
                    updatePassword(updateUserDTO, changePass, mToken);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Your new password must different from your current one", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ImageView btnBack = viewProfile.findViewById(R.id.fm_profile_pass_btn_return);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGender = updateUserDTO.getFirstName().trim();
                mBirth = updateUserDTO.getLastName().trim();
                clickOpenProfileSheetDialog();
            }
        });
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewProfile.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void clickOpenProfileSheetDialog() {
        View viewProfile = getLayoutInflater().inflate(R.layout.fragment_profile, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(viewProfile);
        bottomSheetDialog.show();

        TextView tvUsername = (TextView) viewProfile.findViewById(R.id.fm_profile_tv_user);
        tvUsername.setText(mUsername);
        TextView tvDate = (TextView) viewProfile.findViewById(R.id.fm_profile_et_birthday);
        tvDate.setText(mBirth);
        TextView tvGender = (TextView) viewProfile.findViewById(R.id.fm_profile_et_gender);
        tvGender.setText(mGender);
        TextView tvEmail = (TextView) viewProfile.findViewById(R.id.fm_profile_tv_mail);
        tvEmail.setText(mEmail);
        TextView tvPassword = (TextView) viewProfile.findViewById(R.id.fm_profile_et_pass);

        CardView btnSave = viewProfile.findViewById(R.id.fm_profile_cv_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUserDTO updateUserDTO = new UpdateUserDTO(tvUsername.getText().toString(),
                        tvDate.getText().toString(),
                        tvGender.getText().toString(),
                        tvPassword.getText().toString(), "en");

                Log.e(TAG, "GENDER: " + tvGender.getText().toString());
                clickOpenPassSheetDialog(updateUserDTO);
            }
        });
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewProfile.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }


    private void updateProfile(UpdateUserDTO updateUserDTO, String token) {
        Log.i(TAG, "Start call update profile API");
        APIService updateService = ServiceGenerator.createService(APIService.class);

        Call<ResponseBody> postAccountInfoCaller = updateService.postAccountInfo("Bearer " + token, updateUserDTO);
        Log.e(TAG, "AUTH HEADER: " + "Bearer " + token);
        postAccountInfoCaller.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String status = "\n";
                String message = "\n";
                String details = "\n";
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Update info successfully", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Update info successfully");
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.e(TAG, response.body().string());

                        status = jsonObject.getString("status");
                        if (status.equals(500)) {
                            Log.e(TAG, "NOT SUCCESSFULLY");
                            details = jsonObject.getString("detail");
                            Toast.makeText(getApplicationContext(), details, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.e(TAG, "NOT SUCCESSFULLY");
                            message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                        Log.e(TAG, status + message + details);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "FAILED PARSE OBJECT");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Log.e(TAG, call.toString());
                Toast.makeText(getApplicationContext(), "Cannot post account info!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePassword(UpdateUserDTO updateUserDTO, ChangePass changePass, String token) {
        Log.i(TAG, "Start call CHANGE PASS API");
        APIService changePassService = ServiceGenerator.createService(APIService.class);

        Call<ChangePassResponse> postAccountInfoCaller = changePassService.changePass("Bearer " + token, changePass);
        Log.e(TAG, "AUTH HEADER: " + "Bearer " + token);
        postAccountInfoCaller.enqueue(new Callback<ChangePassResponse>() {

            @Override
            public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response) {
                try {
                    Toast.makeText(getApplicationContext(), "Check your current password again", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, response.body().getTitle());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ChangePassResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Log.e(TAG, call.toString());
//                Toast.makeText(getApplicationContext(), "Change pass successfully", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Change pass successfully");
                updateProfile(updateUserDTO, mToken);
            }
        });
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
//        else if(id==R.id.nav_trash)
//        {
//            openTrashFragment();
//            drawerLayout.closeDrawer(Gravity.LEFT);
//
//        }
        else if(id==R.id.nav_information)
        {
            Intent intent = new Intent(HomeActivity.this, InfoActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_logout)
        {
            openLogoutDialog(Gravity.CENTER);
            //drawerLayout.closeDrawer(Gravity.LEFT);
            //finish();
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