package com.example.team7_app.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.team7_app.R;
import com.example.team7_app.category.Category;
import com.example.team7_app.category.SortCategory;
import com.example.team7_app.category.SortCategoryAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SortFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortFragment extends BottomSheetDialogFragment {

    public SortFragment() {
        // Required empty public constructor
    }

    public static SortFragment newInstance() {
        SortFragment fragment = new SortFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    private Spinner spnSortFileCategory;
    private Spinner spnSortTimeCatergory;
    private SortCategoryAdapter sortCategoryFileTypeAdapter;
    private SortCategoryAdapter sortCategoryTimeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View viewOption = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sort, null);

        bottomSheetDialog.setContentView(viewOption);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewOption.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        spnSortFileCategory = viewOption.findViewById(R.id.fm_sort_spn_filetype);
        sortCategoryFileTypeAdapter = new SortCategoryAdapter(getContext(), R.layout.fragment_sort_item_selected, getListFileCategory());
        spnSortFileCategory.setAdapter(sortCategoryFileTypeAdapter);
        spnSortFileCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(), sortCategoryAdapter.getItem(i).getTypename(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnSortTimeCatergory = viewOption.findViewById(R.id.fm_sort_spn_time);
        sortCategoryTimeAdapter = new SortCategoryAdapter(getContext(), R.layout.fragment_sort_item_selected, getListTimeCategory());
        spnSortTimeCatergory.setAdapter(sortCategoryTimeAdapter);
        spnSortTimeCatergory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(), sortCategoryAdapter.getItem(i).getTypename(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return bottomSheetDialog;
    }

    private List<SortCategory> getListFileCategory() {
        List<SortCategory> list = new ArrayList<>();
        list.add(new SortCategory("File type"));
        list.add(new SortCategory(".txt"));
        list.add(new SortCategory(".pdf"));
        list.add(new SortCategory(".pdf"));
        list.add(new SortCategory(".pdf"));

        return list;

    }

    private List<SortCategory> getListTimeCategory() {
        List<SortCategory> list = new ArrayList<>();
        list.add(new SortCategory("Timeline"));
        list.add(new SortCategory("A day"));
        list.add(new SortCategory("A week"));
        list.add(new SortCategory("A month"));

        return list;

    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialog;
    }
}