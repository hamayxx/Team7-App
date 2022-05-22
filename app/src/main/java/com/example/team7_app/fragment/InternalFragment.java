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

import android.os.StatFs;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.team7_app.File.FileAdapter;
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
 * Use the {@link InternalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InternalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvItems;
    private ImageButton ibBack, ibAdjust;
    private String data;
    private File storage;
    private List<File> fileList;
    private FileAdapter fileAdapter;
    private TextView tvTitle;
    private SearchView svSearch;

    public InternalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InternalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InternalFragment newInstance(String param1, String param2) {
        InternalFragment fragment = new InternalFragment();
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
        return inflater.inflate(R.layout.fragment_internal, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle = getView().findViewById(R.id.fm_internal_tv_title);
        ibBack = getView().findViewById(R.id.fm_internal_btn_return);
        ibAdjust = getView().findViewById(R.id.fm_internal_btn_adjust);
        svSearch = getView().findViewById(R.id.fm_internal_sv_search);

        try {
            data = getArguments().getString("path");
            storage = new File(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvTitle.setText(storage.getName());

        runtimePermission();

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
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

    public ArrayList<File> findFiles(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        if (files != null) {
            for (File singleFile : files) {
                if (singleFile.isDirectory() && !singleFile.isHidden()) {
                        arrayList.add(singleFile);
                } else {
                    if(singleFile.getName().toLowerCase().endsWith(".jpg")
                            || singleFile.getName().toLowerCase().endsWith(".jpeg")
                            || singleFile.getName().toLowerCase().endsWith(".png")
                            || singleFile.getName().toLowerCase().endsWith(".mp3")
                            || singleFile.getName().toLowerCase().endsWith(".mp4")
                            || singleFile.getName().toLowerCase().endsWith(".docx")
                            || singleFile.getName().toLowerCase().endsWith(".doc")
                            || singleFile.getName().toLowerCase().endsWith(".ppt")
                            || singleFile.getName().toLowerCase().endsWith(".pptx")
                            || singleFile.getName().toLowerCase().endsWith(".txt")
                            || singleFile.getName().toLowerCase().endsWith(".pdf")
                            || singleFile.getName().toLowerCase().endsWith(".wav")
                            || singleFile.getName().toLowerCase().endsWith(".apk")
                    )
                    {
                        arrayList.add(singleFile);
                    }
                }
            }
        }
        return arrayList;
    }

    private void displayFiles() {
        rvItems = getView().findViewById(R.id.fm_internal_rv_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvItems.setLayoutManager(linearLayoutManager);
        fileList = new ArrayList<>();
        fileList.addAll(findFiles(storage));
        fileAdapter = new FileAdapter(fileList, new IClickItemOptionListener() {
            @Override
            public void onClickItemOption(File file) {
                clickOpenOptionSheetDialog(file, fileList);
            }

            @Override
            public void onClickFileItem(File file) {
                if (file.isDirectory()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("path", file.getAbsolutePath());
                    InternalFragment internalFragment = new InternalFragment();
                    internalFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, internalFragment).addToBackStack(null).commit();
                } else {
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

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(viewAdjust);
        bottomSheetDialog.show();

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewAdjust.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void clickOpenOptionSheetDialog(File file, List<File> fileList) {
//        View viewOption = getLayoutInflater().inflate(R.layout.fragment_item_options, null);
//
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
//        bottomSheetDialog.setContentView(viewOption);
//        bottomSheetDialog.show();
//
//        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewOption.getParent());
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        MyBottomSheetFragment myBottomSheetFragment = MyBottomSheetFragment.newInstance(file, fileList);
        myBottomSheetFragment.show(getActivity().getSupportFragmentManager(),myBottomSheetFragment.getTag());
    }
}