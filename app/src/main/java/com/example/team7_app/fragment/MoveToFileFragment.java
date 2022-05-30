package com.example.team7_app.fragment;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.team7_app.File.FileAdapter;
import com.example.team7_app.FileOpener;
import com.example.team7_app.R;
import com.example.team7_app.my_interface.IClickItemOptionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoveToFileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoveToFileFragment extends Fragment {

    private RecyclerView rvItems;
    private ImageView btnBack;
    private TextView btnMove;
    private IClickItemOptionListener iClickItemOptionListener;
    // feature
    View mView;
    private File storage;
    private List<File> fileList;
    private FileAdapter fileAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoveToFileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoveToFileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoveToFileFragment newInstance(String param1, String param2) {
        MoveToFileFragment fragment = new MoveToFileFragment();
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
        mView = inflater.inflate(R.layout.fragment_movetofile, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBack = getView().findViewById(R.id.fm_movetofile_iv_back);
        btnMove= getView().findViewById(R.id.fm_movetofile_tv_move);

        String internalStorage = Environment.getExternalStorageDirectory().getPath();
        storage = new File(internalStorage);

        Log.i("TEAM8", "storage:"+ storage +"/"+storage.getPath());

        displayFiles();

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
        rvItems = getView().findViewById(R.id.fm_movetofile_rv_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvItems.setLayoutManager(linearLayoutManager);
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
                else {
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
        arrayList.sort(Comparator.comparing(File::lastModified).reversed());
        return arrayList;
    }
}