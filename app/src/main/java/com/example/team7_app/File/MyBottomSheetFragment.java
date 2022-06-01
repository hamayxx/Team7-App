package com.example.team7_app.File;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7_app.Database.DatabaseHandler;
import com.example.team7_app.FileOpener;
import com.example.team7_app.HomeActivity;
import com.example.team7_app.R;
import com.example.team7_app.fragment.InternalFragment;
import com.example.team7_app.my_interface.IClickItemOptionListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
    private IClickFileOptionListener mIClickFileOptionListener;

    private RecyclerView rvItems;
    private IClickItemOptionListener iClickItemOptionListener;
    // feature
    private File storage;
    private List<File> fileList;
    private FileAdapter fileAdapter;
    private DatabaseHandler db;
    private HomeActivity mHomeActivity;

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

        Bundle bundleReceive = getArguments();
        if(bundleReceive != null)
        {
            mFile = (File) bundleReceive.get(KEY_FILE_OBJ);
        }
//        db = new DatabaseHandler(getContext());
        mHomeActivity =(HomeActivity) getActivity();
        db = mHomeActivity.getDB();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog)  super.onCreateDialog(savedInstanceState);
        View viewOption = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item_options, null);

        bottomSheetDialog.setContentView(viewOption);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewOption.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

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
                openDialogMove();
            }
        });

        btnRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRenameDialog(Gravity.CENTER);
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
    //move
    private void openDialogMove() {
        View viewMove = getLayoutInflater().inflate(R.layout.fragment_movetofile, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(viewMove);
        bottomSheetDialog.show();

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewMove.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        ImageView btnBack = viewMove.findViewById(R.id.fm_movetofile_iv_back);
        TextView btnMove = viewMove.findViewById(R.id.fm_movetofile_tv_move);

        rvItems = viewMove.findViewById(R.id.fm_movetofile_rv_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvItems.setLayoutManager(linearLayoutManager);

        String internalStorage = Environment.getExternalStorageDirectory().getPath();
        storage = new File(internalStorage);
        displayFiles();
        Log.i("TEAM8", "storage:"+ storage +"/"+storage.getPath());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager() != null)
                {
                    getFragmentManager().popBackStack();
                }
            }
        });

        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void displayFiles() {


        fileList = new ArrayList<>();
        fileList.addAll(findFiles(storage));
        fileAdapter = new FileAdapter(fileList, new IClickItemOptionListener() {
            @Override
            public void onClickItemOption(File file) {

            }

            @Override
            public void onClickFileItem(File file) {
                if (file.isDirectory()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("path", file.getAbsolutePath());
                    InternalFragment internalFragment = new InternalFragment();
                    internalFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, internalFragment).addToBackStack(null).commit();
                }
                else {
                    try {
                        FileOpener.openFile(getContext(), file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, getContext());
        rvItems.setAdapter(fileAdapter);
    }

    public ArrayList<File> findFiles(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        if (files != null) {
            for(File singleFile: files)
            {
                if(singleFile.isDirectory() && !singleFile.isHidden())
                {
                    arrayList.addAll(findFiles(singleFile));
                }
//                else {
//                    if(singleFile.getName().toLowerCase().endsWith(".jpg")
//                            || singleFile.getName().toLowerCase().endsWith(".jpeg")
//                            || singleFile.getName().toLowerCase().endsWith(".png")
//                            || singleFile.getName().toLowerCase().endsWith(".mp3")
//                            || singleFile.getName().toLowerCase().endsWith(".mp4")
//                            || singleFile.getName().toLowerCase().endsWith(".docx")
//                            || singleFile.getName().toLowerCase().endsWith(".doc")
//                            || singleFile.getName().toLowerCase().endsWith(".ppt")
//                            || singleFile.getName().toLowerCase().endsWith(".pptx")
//                            || singleFile.getName().toLowerCase().endsWith(".txt")
//                            || singleFile.getName().toLowerCase().endsWith(".pdf")
//                            || singleFile.getName().toLowerCase().endsWith(".wav")
//                            || singleFile.getName().toLowerCase().endsWith(".apk")
//                    )
//                    {
//                        arrayList.add(singleFile);
//                    }
//                }
            }
        }
        arrayList.sort(Comparator.comparing(File::lastModified).reversed());
        return arrayList;
    }

    // Delete
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void deleteFile() {
//        String dest = "/storage/emulated/0/Trash/";
//        File deleteFile = new File(dest + mFile.getName());
//        Log.i("TEAM8", "deleteFile:"+ deleteFile);
//        try {
//            db.insertFile(mFile);
//            Files.move(mFile.toPath(), deleteFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//            Log.i("TEAM8", "mFile deleteFile:" + mFile);
//            Log.i("TEAM8", "mFile originalPath deleteFile:" + mFile.getAbsolutePath());
//
//            Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
//            mIClickFileOptionListener.refreshRecycleView();
//        }
//        catch (IOException exception) {
//            Log.i("TEAM8", "deleteFile:" + exception.toString());
//        }
        mFile.delete();
        Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
    }
    //rename
    private void openRenameDialog(int gravity) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_rename);
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

        TextView tvYes = dialog.findViewById(R.id.dl_popup_rename_tv_yes);
        TextView tvCancel = dialog.findViewById(R.id.dl_popup_rename_tv_cancel);

        tvYes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                EditText name =  dialog.findViewById(R.id.dl_popup_rename_et_text);

                String new_name = name.getEditableText().toString();
                if(new_name.isEmpty())
                {
                    Toast.makeText(getContext(), "Enter the new name!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                String extension = mFile.getAbsolutePath().substring(mFile.getAbsolutePath().lastIndexOf("."));

                File destination = new File(mFile.getAbsolutePath().replace(mFile.getName(), new_name) + extension);
                Log.i("TEAM8", "mFile:"+ mFile);
                Log.i("TEAM8", "destination:"+ destination);
                if(destination.exists())
                {
                    Toast.makeText(getContext(), destination.getName() +" already exist!", Toast.LENGTH_SHORT).show();
                }
                else {

                    try {
                        Files.move(mFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Toast.makeText(getContext(), "Rename success", Toast.LENGTH_SHORT).show();
                        tvName.setText(new_name + extension);
                        dialog.dismiss();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mIClickFileOptionListener = (IClickFileOptionListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IClickFileOptionListener");
        }
    }

    @Override
    public void onDetach() {
        mIClickFileOptionListener = null;
        super.onDetach();
    }

    public interface IClickFileOptionListener {

        void refreshRecycleView();
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialog;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        mIClickFileOptionListener.refreshRecycleView();
    }
}