package com.example.team7_app.fragment;


import android.app.Dialog;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.team7_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String KEY_FILE_OBJ = "file_object";
    private File mFile;
    private TextView tvName ;
    private TextView tvDate ;
    private TextView tvSize ;
    private CardView btnEdit ;

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
        tvName = view.findViewById(R.id.fm_item_tv_nameFile);
        tvDate = view.findViewById(R.id.fm_item_tv_date);
        tvSize = view.findViewById(R.id.fm_item_tv_size);
        btnEdit = view.findViewById(R.id.fm_item_cv_open);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void setDataFile()
    {
        if(mFile == null)
        {
            return;
        }

        tvName.setText(mFile.getName());
        Date lastModified = new Date(mFile.lastModified());
        SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = simpleDateFormatter.format(lastModified);
        tvDate.setText("Last modified: "+lastModified);
        tvSize.setText("Size : "+ Formatter.formatShortFileSize(getContext(), mFile.length()));

    }
}
