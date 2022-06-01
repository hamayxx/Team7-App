package com.example.team7_app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7_app.CustomProgressBar.CustomProgressBar;
import com.example.team7_app.CustomProgressBar.ProgressItem;
import com.example.team7_app.HomeActivity;
import com.example.team7_app.R;
import com.example.team7_app.category.Category;
import com.example.team7_app.category.CategoryAdapter;
import com.example.team7_app.my_interface.IClickHomeListener;
import com.example.team7_app.my_interface.IClickItemCategoryListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ERROR = "ERROR";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CustomProgressBar customProgressBar;
    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;
    private RecyclerView rvCategories;
    private CategoryAdapter categoryAdapter;
    private IClickItemCategoryListener iClickItemCategoryListener;
    private TextView tvHello;
    private TextView tvRemainStorage;
    private TextView tvTotalStorage;
    private HomeActivity mHomeActivity;
    private long[] sizeCategories;
    /*private GetStatusTask getStatusTask;*/

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        mHomeActivity =(HomeActivity) getActivity();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCategories = getView().findViewById(R.id.fm_home_rv_categories);
        customProgressBar = getView().findViewById(R.id.fm_home_pb_mainstorage);
        tvHello = getView().findViewById(R.id.fm_home_tv_hello);
        tvRemainStorage = getView().findViewById(R.id.fm_home_tv_remaining_size);
        tvTotalStorage = getView().findViewById(R.id.fm_home_tv_total_size);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rvCategories.setLayoutManager(linearLayoutManager);

        tvHello.setText("Hello, " + mHomeActivity.getUsername());

        sizeCategories = new long[8];
        for (int i = 0; i < 8; i++) {
            sizeCategories[i] = 0;
        }

        findSizeFiles(Environment.getExternalStorageDirectory());

        categoryAdapter = new CategoryAdapter(getListCategory(), new IClickItemCategoryListener() {
            @Override
            public void onClickItemCategory(Category category) {
                IClickHomeListener iClickHomeListener = (IClickHomeListener) getActivity();
                Bundle bundle = new Bundle();
                DocumentsFragment documentsFragment = new DocumentsFragment();
                switch (category.getTitle()) {
                    case "Documents":
                        bundle.putString("nameCategory","Documents");
                        documentsFragment.setArguments(bundle);
                        iClickHomeListener.setCurrentFragment(4);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, documentsFragment).addToBackStack(DocumentsFragment.documentTag).commit();
                        break;
                    case "Downloads":
                        bundle.putString("nameCategory","Downloads");
                        documentsFragment.setArguments(bundle);
                        iClickHomeListener.setCurrentFragment(4);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, documentsFragment).addToBackStack(DocumentsFragment.documentTag).commit();
                        break;
                    case "Images":
                        bundle.putString("nameCategory","Images");
                        documentsFragment.setArguments(bundle);
                        iClickHomeListener.setCurrentFragment(4);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, documentsFragment).addToBackStack(DocumentsFragment.documentTag).commit();
                        break;
                    case "Musics":
                        bundle.putString("nameCategory","Music");
                        documentsFragment.setArguments(bundle);
                        iClickHomeListener.setCurrentFragment(4);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, documentsFragment).addToBackStack(DocumentsFragment.documentTag).commit();
                        break;
                    case "Videos":
                        bundle.putString("nameCategory","Videos");
                        documentsFragment.setArguments(bundle);
                        iClickHomeListener.setCurrentFragment(4);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, documentsFragment).addToBackStack(DocumentsFragment.documentTag).commit();
                        break;
                    default:
                        break;
                }

            }
        }, getContext());

        rvCategories.setAdapter(categoryAdapter);

        customProgressBar.getThumb().mutate().setAlpha(0);
        initDataToSeekbar();

        tvTotalStorage.setText(getTotalExternalMemorySize(getContext()));
        tvRemainStorage.setText(getUsedExternalMemorySize(getContext()));
    }

    private List<Category> getListCategory() {
        List<Category> listCategory = new ArrayList<>();

        listCategory.add(new Category("Documents", R.drawable.icon_document, getStatus("documents", getContext()), R.color.red1));
        listCategory.add(new Category("Images", R.drawable.icon_pic, getStatus("images", getContext()), R.color.red2));
        listCategory.add(new Category("Videos", R.drawable.icon_mp4, getStatus("videos", getContext()), R.color.red3));
        listCategory.add(new Category("Musics", R.drawable.icon_mp3, getStatus("music", getContext()), R.color.red4));
        listCategory.add(new Category("Downloads", R.drawable.icon_download, getStatus("downloads", getContext()), R.color.red5));

        return listCategory;
    }

    private void initDataToSeekbar() {
        File path = Environment.getExternalStorageDirectory();
        //File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long totalBlocks = stat.getTotalBytes();
        File pathDownloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        progressItemList = new ArrayList<ProgressItem>();
        //System span
        mProgressItem = new ProgressItem();
        long remaining = totalBlocks - sizeCategories[0] - sizeCategories[2]
                - sizeCategories[4] - sizeCategories[6] - folderSize(pathDownloads);
        long other = totalBlocks - remaining - sizeCategories[0] - sizeCategories[2]
                - sizeCategories[4] - sizeCategories[6] - folderSize(pathDownloads);
        mProgressItem.progressItemPercentage = ((float) other/totalBlocks)*100;
        mProgressItem.color =  R.color.yellow2;
        progressItemList.add(mProgressItem);
        //Documents span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((float) sizeCategories[0]/totalBlocks)*100;
        mProgressItem.color = R.color.red1;
        progressItemList.add(mProgressItem);
        //Images span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((float) sizeCategories[6]/totalBlocks)*100;
        mProgressItem.color = R.color.red2;
        progressItemList.add(mProgressItem);
        //Videos span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((float) sizeCategories[2]/totalBlocks)*100;
        mProgressItem.color = R.color.red3;
        progressItemList.add(mProgressItem);
        //Music span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((float) sizeCategories[4]/totalBlocks)*100;
        mProgressItem.color =  R.color.red4;
        progressItemList.add(mProgressItem);
        //Downloads span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage =  ((float) folderSize(pathDownloads)/totalBlocks)*100;
        mProgressItem.color =  R.color.red5;
        progressItemList.add(mProgressItem);
        //Remaining span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((float) remaining/ totalBlocks) * 100;
        mProgressItem.color =  R.color.white;
        progressItemList.add(mProgressItem);

        customProgressBar.initData(progressItemList);
        customProgressBar.invalidate();
    }

    public String getStatus(String categoryName, Context context) {
        switch (categoryName) {
            case "downloads":
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                return Formatter.formatShortFileSize(context, folderSize(path)) + " (" + path.listFiles().length + ")";
            case "documents":
                return Formatter.formatShortFileSize(context, sizeCategories[0]) + " (" + sizeCategories[1] + ")";
            case "videos":
                return Formatter.formatShortFileSize(context, sizeCategories[2]) + " (" + sizeCategories[3] + ")";
            case "music":
                return Formatter.formatShortFileSize(context, sizeCategories[4]) + " (" + sizeCategories[5] + ")";
            case "images":
                return Formatter.formatShortFileSize(context, sizeCategories[6]) + " (" + sizeCategories[7] + ")";
        }
        return "";
    }

    private void findSizeFiles(File file) { // 0,1: document 2,3: video 4,5: music 6,7: image
        File[] files = file.listFiles();

        if (files != null) {
            for(File singleFile: files)
            {
                if(singleFile.isDirectory() && !singleFile.isHidden())
                {
                    findSizeFiles(singleFile);
                }
                else {
                    if (singleFile.getName().toLowerCase().endsWith(".docs")
                            || singleFile.getName().toLowerCase().endsWith(".doc")
                            || singleFile.getName().toLowerCase().endsWith(".txt")
                            || singleFile.getName().toLowerCase().endsWith(".ppt")
                            || singleFile.getName().toLowerCase().endsWith(".pptx")
                            || singleFile.getName().toLowerCase().endsWith(".pdf")) {
                        sizeCategories[0] += singleFile.length();
                        sizeCategories[1]++;
                    }
                    else if (singleFile.getName().toLowerCase().endsWith(".mp4")) {
                        sizeCategories[2] += singleFile.length();
                        sizeCategories[3]++;
                    }
                    else if (singleFile.getName().toLowerCase().endsWith(".mp3")
                            || singleFile.getName().toLowerCase().endsWith(".wav")){
                        sizeCategories[4] += singleFile.length();
                        sizeCategories[5]++;
                    }
                    else if (singleFile.getName().toLowerCase().endsWith(".jpg")
                            || singleFile.getName().toLowerCase().endsWith(".jpeg")
                            || singleFile.getName().toLowerCase().endsWith(".png")) {
                        sizeCategories[6] += singleFile.length();
                        sizeCategories[7]++;
                    }
                }
            }
        }
    }

    public static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return length;
    }

    public static String getAvailableInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return Formatter.formatShortFileSize(context, availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return Formatter.formatShortFileSize(context, totalBlocks * blockSize);
    }

    public static String getUsedInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return Formatter.formatShortFileSize(context, (totalBlocks - availableBlocks) * blockSize);
    }

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableExternalMemorySize(Context context) {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return Formatter.formatShortFileSize(context, availableBlocks * blockSize);
        } else {
            return ERROR;
        }
    }

    public static String getTotalExternalMemorySize(Context context) {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return Formatter.formatShortFileSize(context, totalBlocks * blockSize);
        } else {
            return ERROR;
        }
    }

    public static String getUsedExternalMemorySize(Context context) {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            long totalBlocks = stat.getBlockCountLong();
            return Formatter.formatShortFileSize(context, (totalBlocks - availableBlocks) * blockSize);
        } else {
            return ERROR;
        }
    }

}