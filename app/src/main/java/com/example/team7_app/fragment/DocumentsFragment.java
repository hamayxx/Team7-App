package com.example.team7_app.fragment;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.team7_app.File.FileAdapter;
import com.example.team7_app.File.MyBottomSheetFragment;
import com.example.team7_app.FileOpener;
import com.example.team7_app.R;
import com.example.team7_app.my_interface.IClickItemOptionListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocumentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rvItems;
    private ImageButton ibBack, ibAdjust;
    public static final String documentTag = DocumentsFragment.class.getName();

    private IClickItemOptionListener iClickItemOptionListener;
    // feature
    View mView;
    private File storage;
    private List<File> fileList;
    private FileAdapter fileAdapter;
    private String nameCategory;
    private TextView tvTitle;
    private TextView tvSizeCount;
    private ProgressBar pbTotalUsed;
    private SearchView svSearch;
    private long size;
    private int count;

    public DocumentsFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocumentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocumentsFragment newInstance(String param1, String param2) {
        DocumentsFragment fragment = new DocumentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_documents, container, false);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle = getView().findViewById(R.id.fm_documents_tv_title);
        ibBack = getView().findViewById(R.id.fm_documents_btn_return);
        ibAdjust = getView().findViewById(R.id.fm_documents_btn_adjust);
        tvSizeCount = getView().findViewById(R.id.fm_documents_tv_gb_item);
        pbTotalUsed = getView().findViewById(R.id.fm_documents_pb_total_used);
        svSearch = getView().findViewById(R.id.fm_documents_sv_search);

        try {
            nameCategory = getArguments().getString("nameCategory");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //feature
        //String internalStorage = Environment.getExternalStorageDirectory().getPath();

        if (nameCategory.equals("Downloads")) {
            String documentStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            storage = new File(documentStorage);
            tvTitle.setText("DOWNLOADS");
        }
        else {
            String internalStorage = Environment.getExternalStorageDirectory().getPath();
            storage = new File(internalStorage);
            switch (nameCategory) {
                case "Documents":
                    tvTitle.setText("DOCUMENTS");
                    break;
                case "Music":
                    tvTitle.setText("MUSIC");
                    break;
                case "Videos":
                    tvTitle.setText("VIDEOS");
                    break;
                case "Images":
                    tvTitle.setText("IMAGES");
                    break;
            }

        }

        displayFiles();

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager() != null)
                {
                    getFragmentManager().popBackStack();
                }
            }
        });

        ibAdjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOpenAdjustSheetDialog();
            }
        });

        // search
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileAdapter.searchItem(search(newText));
                return false;
            }
        });
    }

    private void runtimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Dexter.withContext(getContext()).withPermissions(
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            displayFiles();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
        } else {
            Dexter.withContext(getContext()).withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            displayFiles();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
        }
    }

    public ArrayList<File> findFiles(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        if (files != null) {
            for(File singleFile: files)
            {
                if(singleFile.isDirectory() && !singleFile.isHidden())
                {
                    if (nameCategory.equals("Downloads"))
                    {
                        arrayList.add(singleFile);
                    }
                    else {
                        arrayList.addAll(findFiles(singleFile));
                    }
                }
                else {
                    switch (nameCategory) {
                        case "Documents":
                            if (singleFile.getName().toLowerCase().endsWith(".docx")
                                    || singleFile.getName().toLowerCase().endsWith(".doc")
                                    || singleFile.getName().toLowerCase().endsWith(".txt")
                                    || singleFile.getName().toLowerCase().endsWith(".ppt")
                                    || singleFile.getName().toLowerCase().endsWith(".pptx")
                                    || singleFile.getName().toLowerCase().endsWith(".pdf")) {
                                size += singleFile.length();
                                arrayList.add(singleFile);
                            }
                            break;
                        case "Images":
                            if (singleFile.getName().toLowerCase().endsWith(".jpg")
                                    || singleFile.getName().toLowerCase().endsWith(".jpeg")
                                    || singleFile.getName().toLowerCase().endsWith(".png")) {
                                size += singleFile.length();
                                arrayList.add(singleFile);
                            }
                            break;
                        case "Videos":
                            if (singleFile.getName().toLowerCase().endsWith(".mp4")) {
                                size += singleFile.length();
                                arrayList.add(singleFile);
                            }
                            break;
                        case "Music":
                            if (singleFile.getName().toLowerCase().endsWith(".mp3")
                                    || singleFile.getName().toLowerCase().endsWith(".wav")) {
                                size += singleFile.length();
                                arrayList.add(singleFile);
                            }
                            break;
                        case "Downloads":
                            arrayList.add(singleFile);
                            size += singleFile.length();
                            break;
                    }
                }
            }
        }
        return arrayList;
    }

    private void displayFiles() {
        rvItems = getView().findViewById(R.id.fm_documents_rv_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvItems.setLayoutManager(linearLayoutManager);
        fileList = new ArrayList<>();
        fileList.addAll(findFiles(storage));
        fileAdapter = new FileAdapter(fileList, new IClickItemOptionListener() {
            @Override
            public void onClickItemOption(File file) {
                clickOpenOptionSheetDialog(file);
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
        count = fileList.size();
        tvSizeCount.setText(Formatter.formatShortFileSize(getContext(), size) + "/ " + count + " Items");

        StatFs stat = new StatFs(storage.getPath());
        long totalBlocks = stat.getTotalBytes();
        int proGr = (int) ((float) size/totalBlocks * 100);
        pbTotalUsed.setProgress(proGr);
    }

    // search
    private ArrayList<File> search(String text) {
        ArrayList<File> arrayList = new ArrayList<>();
        if (fileList != null) {
            for (File singleFile: fileList) {
                if (singleFile.getName().toLowerCase().contains(text.toLowerCase())) {
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }

    private void clickOpenAdjustSheetDialog() {
        View viewAdjust = getLayoutInflater().inflate(R.layout.fragment_sort, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(viewAdjust);
        bottomSheetDialog.show();

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewAdjust.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void clickOpenOptionSheetDialog(File file) {
        MyBottomSheetFragment myBottomSheetFragment = MyBottomSheetFragment.newInstance(file);
        myBottomSheetFragment.show(getActivity().getSupportFragmentManager(),myBottomSheetFragment.getTag());
    }
}