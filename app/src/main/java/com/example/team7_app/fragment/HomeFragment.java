package com.example.team7_app.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team7_app.CustomProgressBar.CustomProgressBar;
import com.example.team7_app.CustomProgressBar.ProgressItem;
import com.example.team7_app.HomeActivity;
import com.example.team7_app.R;
import com.example.team7_app.category.Category;
import com.example.team7_app.category.CategoryAdapter;
import com.example.team7_app.my_interface.IClickHomeListener;
import com.example.team7_app.my_interface.IClickItemCategoryListener;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CustomProgressBar customProgressBar;
    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;
    private RecyclerView rvCategories;
    private CategoryAdapter categoryAdapter;
    private IClickItemCategoryListener iClickItemCategoryListener;

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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCategories = getView().findViewById(R.id.fm_home_rv_categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rvCategories.setLayoutManager(linearLayoutManager);

        categoryAdapter = new CategoryAdapter(getListCategory(), new IClickItemCategoryListener() {
            @Override
            public void onClickItemCategory(Category category) {
                IClickHomeListener iClickHomeListener = (IClickHomeListener) getActivity();
                switch (category.getTitle()) {
                    case "Documents":
                        iClickHomeListener.setCurrentFragment(4);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DocumentsFragment()).addToBackStack(DocumentsFragment.documentTag).commit();
                        break;
                    case "Downloads":
                        iClickHomeListener.setCurrentFragment(4);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DownloadsFragment()).addToBackStack(DownloadsFragment.downloadTag).commit();
                        break;
                    case "Media":
                        iClickHomeListener.setCurrentFragment(4);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MediaFragment()).addToBackStack(MediaFragment.mediaTag).commit();
                        break;
                    case "Apps":
                        iClickHomeListener.setCurrentFragment(4);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new AppsFragment()).addToBackStack(AppsFragment.appTag).commit();
                        break;
                    default:
                        break;
                }

            }
        });
        categoryAdapter.setData(getListCategory());
        rvCategories.setAdapter(categoryAdapter);

        customProgressBar = getView().findViewById(R.id.fm_home_pb_mainstorage);
        customProgressBar.getThumb().mutate().setAlpha(0);
        initDataToSeekbar();
    }

    private List<Category> getListCategory() {
        List<Category> listCategory = new ArrayList<>();

        listCategory.add(new Category("Documents", R.drawable.icon_document, "26.7 GB (138)"));
        listCategory.add(new Category("Downloads", R.drawable.icon_download, "435 MB (43)"));
        listCategory.add(new Category("Media", R.drawable.icon_media, "1.2 GB (1384)"));
        listCategory.add(new Category("Apps", R.drawable.icon_app, "1.6 GB (92)"));

        return listCategory;
    }

    private void initDataToSeekbar() {
        progressItemList = new ArrayList<ProgressItem>();
        // red span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 20;
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);
        // blue span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 25;
        mProgressItem.color = R.color.blue;
        progressItemList.add(mProgressItem);
        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 35;
        mProgressItem.color = R.color.green;
        progressItemList.add(mProgressItem);

        //white span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 20;
        mProgressItem.color =  R.color.white;
        progressItemList.add(mProgressItem);


        customProgressBar.initData(progressItemList);
        customProgressBar.invalidate();
    }
}