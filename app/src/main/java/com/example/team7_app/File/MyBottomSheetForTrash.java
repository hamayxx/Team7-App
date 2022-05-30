package com.example.team7_app.File;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.example.team7_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBottomSheetForTrash extends BottomSheetDialogFragment {
    private static final String KEY_FILE_OBJ = "file_object";
    private File mFile;
    private TextView tvName ;
    private TextView tvDate ;
    private TextView tvSize ;
    private TextView tvOriginalPath ;
    private CardView btnRestore,  btnDelete ;
    private ImageView ivIcon;



    public static MyBottomSheetForTrash newInstance(File file){
        MyBottomSheetForTrash myBottomSheetForTrash = new MyBottomSheetForTrash();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_FILE_OBJ, file);
        myBottomSheetForTrash.setArguments(bundle);

        return myBottomSheetForTrash;
    }

        @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundleRecvive = getArguments();
        if(bundleRecvive != null)
        {
            mFile = (File) bundleRecvive.get(KEY_FILE_OBJ);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog)  super.onCreateDialog(savedInstanceState);
        View viewOption = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item_options, null);

        bottomSheetDialog.setContentView(viewOption);

        initView(viewOption);
        setDataFile();

        return bottomSheetDialog;

    }
    private void  initView(View view)
    {
        ivIcon = view.findViewById(R.id.fm_item_options_trash_iv_iconfile);
        tvName = view.findViewById(R.id.fm_item_options_trash_tv_filename);
        tvName.setSelected(true);
        tvDate = view.findViewById(R.id.fm_item_options_trash_tv_date);
        tvSize = view.findViewById(R.id.fm_item_options_trash_tv_size);
        tvOriginalPath = view.findViewById(R.id.fm_item_options_trash_tv_origin);
        btnRestore = view.findViewById(R.id.fm_item_options_trash_iv_restore);
        btnDelete = view.findViewById(R.id.fm_item_options_trash_iv_restore);


        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoreFile();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                deleteFile();
            }
        });
    }

    private void restoreFile() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void deleteFile() {

        openDeleteDialog(Gravity.CENTER);

    }

    private void openDeleteDialog(int gravity) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_deleteall);
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

        TextView tvYes = dialog.findViewById(R.id.dl_popup_deleteAll_tv_question);
        TextView tvCancel = dialog.findViewById(R.id.dl_popup_deleteAll_logout_cancel);

        tvYes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                mFile.delete();
                Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }

        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    private void setDataFile()
    {
        if(mFile == null)
        {
            return;
        }

        if(mFile.getName().endsWith(".jpeg"))
        {
            ivIcon.setBackgroundResource(R.drawable.ic_image);
        }
        else if(mFile.getName().endsWith(".jpg")){
            ivIcon.setBackgroundResource(R.drawable.ic_image);
        }
        else if(mFile.getName().endsWith(".png")){
            ivIcon.setBackgroundResource(R.drawable.ic_image);
        }
        else if(mFile.getName().endsWith(".pdf")){
            ivIcon.setBackgroundResource(R.drawable.ic_pdf);
        }
        else if(mFile.getName().endsWith(".doc")){
            ivIcon.setBackgroundResource(R.drawable.ic_docs);
        }
        else if(mFile.getName().endsWith(".docx")){
            ivIcon.setBackgroundResource(R.drawable.ic_docs);
        }
        else if(mFile.getName().endsWith(".txt")){
            ivIcon.setBackgroundResource(R.drawable.icon_txt);
        }
        else if(mFile.getName().endsWith(".ppt")){
            ivIcon.setBackgroundResource(R.drawable.icon_ppt);
        }
        else if(mFile.getName().endsWith(".pptx")){
            ivIcon.setBackgroundResource(R.drawable.icon_ppt);
        }
        else if(mFile.getName().endsWith(".mp3")){
            ivIcon.setBackgroundResource(R.drawable.ic_music);
        }
        else if(mFile.getName().endsWith(".wav")){
            ivIcon.setBackgroundResource(R.drawable.ic_music);
        }
        else if(mFile.getName().endsWith(".mp4")){
            ivIcon.setBackgroundResource(R.drawable.ic_play);
        }
        else if(mFile.getName().endsWith(".apk")){
            ivIcon.setBackgroundResource(R.drawable.ic_android);
        }
        else{
            ivIcon.setBackgroundResource(R.drawable.folder);
        }

        tvName.setText(mFile.getName());
        Date lastModified = new Date(mFile.lastModified());
        SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = simpleDateFormatter.format(lastModified);
        tvDate.setText("Last modified: "+ formattedDate);
        tvSize.setText("Size : "+ Formatter.formatShortFileSize(getContext(), mFile.length()));

    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialog;
    }
}
