package com.example.team7_app.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.LabeledIntent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.example.team7_app.File.FileAdapter;
import com.example.team7_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MyBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String KEY_FILE_OBJ = "file_object";
    private File mFile;
    private TextView tvName ;
    private TextView tvDate ;
    private TextView tvSize ;
    private CardView btnMove, btnRename, btnDelete ;
    private ImageView ivIcon;


    public static MyBottomSheetFragment newInstance(File file){
        MyBottomSheetFragment myBottomSheetFragment = new MyBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_FILE_OBJ, file);

        myBottomSheetFragment.setArguments(bundle);

        return myBottomSheetFragment;
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
        ivIcon = view.findViewById(R.id.fm_item_options_iv_iconfile);
        tvName = view.findViewById(R.id.fm_item_options_tv_filename);
        tvName.setSelected(true);
        tvDate = view.findViewById(R.id.fm_item_options_tv_date);
        tvSize = view.findViewById(R.id.fm_item_options_tv_size);
        btnMove = view.findViewById(R.id.fm_item_options_cv_move);
        btnRename = view.findViewById(R.id.fm_item_options_cv_rename);
        btnDelete = view.findViewById(R.id.fm_item_options_cv_delete);


        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveFile();
            }
        });

        btnRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameFile();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void deleteFile() {

        //  Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();

        String dest = "/storage/emulated/0/Trash/";
        File deleteFile = new File(dest + mFile.getName());
        File originalFile = new File(mFile.getAbsolutePath());
        Log.i("TEAM8", "deleteFile:"+ deleteFile);

        try {
            Files.move(mFile.toPath(), deleteFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException exception)
        {
            Log.i("TEAM8", "deleteFile:"+ exception.toString());

        }
    }

    private void renameFile() {
        AlertDialog.Builder renameDailog = new AlertDialog.Builder(getContext());
        renameDailog.setTitle("Rename File");
        final EditText name = new EditText(getContext());
        renameDailog.setView(name);

        renameDailog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String new_name = name.getEditableText().toString();
                String extention = mFile.getAbsolutePath().substring(mFile.getAbsolutePath().lastIndexOf("."));

                File destination = new File(mFile.getAbsolutePath().replace(mFile.getName(), new_name) + extention);
                Log.i("TEAM8", "mFile:"+ mFile);
                Log.i("TEAM8", "destination:"+ destination);

                if(destination.exists())
                {
                    Toast.makeText(getContext(), destination.getName() +" already exist!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    try {
                        Files.move(mFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog_rename = renameDailog.create();
        alertDialog_rename.show();
    }

    private void moveFile() {
        Toast.makeText(getContext(), "Move", Toast.LENGTH_SHORT).show();

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