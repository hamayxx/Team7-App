package com.example.team7_app.fragment;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.team7_app.File.FileAdapter;
import com.example.team7_app.HomeActivity;
import com.example.team7_app.R;
import com.example.team7_app.item.Item;
import com.example.team7_app.item.ItemAdapter;
import com.example.team7_app.my_interface.IClickItemOptionListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
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
    private ItemAdapter itemAdapter;
    private ImageButton ibBack, ibAdjust;
    public static final String documentTag = DocumentsFragment.class.getName();

    private IClickItemOptionListener iClickItemOptionListener;
    // feature
    View mView;
    File storage;
    private List<File> fileList;
    private FileAdapter fileAdapter;
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
        mView=  inflater.inflate(R.layout.fragment_documents, container, false);

        //feature
        String internalStorage= System.getenv("EXTERNAL_STORAGE");
        storage= new File(internalStorage);

        runtimePermission();
        return mView;
    }

    private void runtimePermission() {
        Dexter.withContext(getContext()).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
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

    public ArrayList<File> findFiles(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        for(File singleFile: files)
        {
            if(!singleFile.isDirectory() && !singleFile.isHidden())
            {
                arrayList.add(singleFile);
            }
        }
        for(File singleFile: files)
        {
            if(singleFile.getName().toLowerCase().endsWith(".jpg")
            || singleFile.getName().toLowerCase().endsWith(".jpeg")
            || singleFile.getName().toLowerCase().endsWith(".png")
            || singleFile.getName().toLowerCase().endsWith(".mp3")
            || singleFile.getName().toLowerCase().endsWith(".mp4")
            || singleFile.getName().toLowerCase().endsWith(".docs")
            || singleFile.getName().toLowerCase().endsWith(".pdf")
            || singleFile.getName().toLowerCase().endsWith(".wav")
            || singleFile.getName().toLowerCase().endsWith(".apk")
            )
            {
                arrayList.add(singleFile);
            }
        }
        return arrayList;
    }
    private void displayFiles() {
        rvItems= getView().findViewById(R.id.fm_documents_rv_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvItems.setLayoutManager(linearLayoutManager);
        fileList= new ArrayList<>();
        fileList.addAll(findFiles(storage));
        fileAdapter = new FileAdapter(getContext(),fileList);
        rvItems.setAdapter(fileAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        rvItems = getView().findViewById(R.id.fm_documents_rv_items);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        rvItems.setLayoutManager(linearLayoutManager);
//
//        //mo option item
//        itemAdapter = new ItemAdapter(getListItem(), new IClickItemOptionListener() {
//            @Override
//            public void onClickItemOption(Item item) {
//                clickOpenOptionSheetDialog();
//            }
//        });
//        itemAdapter.setData(getListItem());
//        rvItems.setAdapter(itemAdapter);

        ibBack = getView().findViewById(R.id.fm_documents_btn_return);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager() != null)
                {
                    getFragmentManager().popBackStack();
                }
            }
        });

        ibAdjust = getView().findViewById(R.id.fm_documents_btn_adjust);
        ibAdjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOpenAdjustSheetDialog();
            }
        });
    }

    private List<Item> getListItem() {
        List<Item> listItem = new ArrayList<>();

        listItem.add(new Item(R.drawable.icon_pdf, "Draft.txt", "17 March 2022 | Used: 123MB"));
        listItem.add(new Item(R.drawable.icon_pdf, "Draft.txt", "17 March 2022 | Used: 123MB"));
        listItem.add(new Item(R.drawable.icon_pdf, "Draft.txt", "17 March 2022 | Used: 123MB"));
        listItem.add(new Item(R.drawable.icon_pdf, "Draft.txt", "17 March 2022 | Used: 123MB"));

        return listItem;
    }
    private void clickOpenAdjustSheetDialog() {
        View viewAdjust = getLayoutInflater().inflate(R.layout.fragment_sort, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(viewAdjust);
        bottomSheetDialog.show();

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewAdjust.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void clickOpenOptionSheetDialog() {
        View viewOption = getLayoutInflater().inflate(R.layout.fragment_item_options, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(viewOption);
        bottomSheetDialog.show();

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewOption.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}