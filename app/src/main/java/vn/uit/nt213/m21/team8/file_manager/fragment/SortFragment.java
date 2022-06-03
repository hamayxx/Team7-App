package vn.uit.nt213.m21.team8.file_manager.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import vn.uit.nt213.m21.team8.file_manager.R;
import vn.uit.nt213.m21.team8.file_manager.category.SortCategoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SortFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortFragment extends BottomSheetDialogFragment {

    public SortFragment() {
        // Required empty public constructor
    }

    public static SortFragment newInstance(ArrayList<String> listFileExtensions, String sortStatus, String filterFileStatus, String filterTimeStatus) {
        SortFragment fragment = new SortFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("listExtensions", listFileExtensions);
        bundle.putString("sort", sortStatus);
        bundle.putString("file", filterFileStatus);
        bundle.putString("time", filterTimeStatus);
        
        fragment.setArguments(bundle);
        return fragment;
    }

    private Spinner spnSortFileCategory;
    private Spinner spnSortTimeCategory;
    private SortCategoryAdapter sortCategoryFileTypeAdapter;
    private SortCategoryAdapter sortCategoryTimeAdapter;
    private CardView cvAZ, cvZA, cvDescSize, cvIncrSize;
    private TextView tvReset;
    private String sortStatus = "null";
    private String filterFileStatus = "File Type";
    private String filterTimeStatus = "Timeline";
    private ArrayList<String> listExtensions;
    private IClickSortListener mIClickSortListenerCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listExtensions = getArguments().getStringArrayList("listExtensions");
            sortStatus = getArguments().getString("sort");
            filterFileStatus = getArguments().getString("file");
            filterTimeStatus = getArguments().getString("time");
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

        cvAZ = viewOption.findViewById(R.id.fm_sort_az);
        cvZA = viewOption.findViewById(R.id.fm_sort_za);
        cvIncrSize = viewOption.findViewById(R.id.fm_sort_incr_size);
        cvDescSize = viewOption.findViewById(R.id.fm_sort_decr_size);
        tvReset = viewOption.findViewById(R.id.fm_sort_tv_reset);
        spnSortFileCategory = viewOption.findViewById(R.id.fm_sort_spn_filetype);

        sortCategoryFileTypeAdapter = new SortCategoryAdapter(getContext(), R.layout.fragment_sort_item_selected, listExtensions);
        spnSortFileCategory.setAdapter(sortCategoryFileTypeAdapter);
        spnSortFileCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    filterFileStatus = sortCategoryFileTypeAdapter.getItem(i);
                    mIClickSortListenerCallback.updateSort(sortStatus, filterFileStatus, filterTimeStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnSortTimeCategory = viewOption.findViewById(R.id.fm_sort_spn_time);
        sortCategoryTimeAdapter = new SortCategoryAdapter(getContext(), R.layout.fragment_sort_item_selected, getListTimeCategory());
        spnSortTimeCategory.setAdapter(sortCategoryTimeAdapter);
        spnSortTimeCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterTimeStatus = sortCategoryTimeAdapter.getItem(i);
                mIClickSortListenerCallback.updateSort(sortStatus, filterFileStatus, filterTimeStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        settingUpStatus();

        cvAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cvAZ.getBackgroundTintList() != getContext().getResources().getColorStateList(R.color.red2)) {
                    cvAZ.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red2));
                    cvZA.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    cvIncrSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    cvDescSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    sortStatus = "az";
                    mIClickSortListenerCallback.updateSort(sortStatus, filterFileStatus, filterTimeStatus);
                }
            }
        });

        cvZA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cvZA.getBackgroundTintList() != getContext().getResources().getColorStateList(R.color.red2)) {
                    cvZA.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red2));
                    cvAZ.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    cvIncrSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    cvDescSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    sortStatus = "za";
                    mIClickSortListenerCallback.updateSort(sortStatus, filterFileStatus, filterTimeStatus);
                }
            }
        });

        cvIncrSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cvIncrSize.getBackgroundTintList() != getContext().getResources().getColorStateList(R.color.red2)) {
                    cvIncrSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red2));
                    cvAZ.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    cvZA.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    cvDescSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    sortStatus = "incrSize";
                    mIClickSortListenerCallback.updateSort(sortStatus, filterFileStatus, filterTimeStatus);
                }
            }
        });

        cvDescSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cvDescSize.getBackgroundTintList() != getContext().getResources().getColorStateList(R.color.red2)) {
                    cvDescSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red2));
                    cvAZ.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    cvZA.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    cvIncrSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                    sortStatus = "descSize";
                    mIClickSortListenerCallback.updateSort(sortStatus, filterFileStatus, filterTimeStatus);
                }
            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvDescSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                cvAZ.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                cvZA.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                cvIncrSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main_menu_background));
                spnSortFileCategory.setSelection(sortCategoryFileTypeAdapter.getPosition("File Type"));
                spnSortTimeCategory.setSelection(sortCategoryTimeAdapter.getPosition("Timeline"));
                mIClickSortListenerCallback.resetSort();
            }
        });

        return bottomSheetDialog;
    }

    private List<String> getListTimeCategory() {
        List<String> list = new ArrayList<>();
        list.add("Timeline");
        list.add("A day");
        list.add("A week");
        list.add("A month");

        return list;

    }

    private void settingUpStatus() {
        if (!sortStatus.equals("null")) {
            switch (sortStatus) {
                case "az":
                    cvAZ.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red2));
                    break;
                case "za":
                    cvZA.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red2));
                    break;
                case "descSize":
                    cvDescSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red2));
                    break;
                case "incrSize":
                    cvIncrSize.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red2));
                    break;
            }
        }

        if (!filterFileStatus.equals("File Type")) {
            spnSortFileCategory.setSelection(sortCategoryFileTypeAdapter.getPosition(filterFileStatus));
        }

        if (!filterTimeStatus.equals("Timeline")) {
            spnSortTimeCategory.setSelection(sortCategoryTimeAdapter.getPosition(filterTimeStatus));
        }
    }

    /*@Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            mIClickSortListenerCallback = (IClickSortListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement IClickSortListener");
        }
    }*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mIClickSortListenerCallback = (IClickSortListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IClickSortListener");
        }
    }

    @Override
    public void onDetach() {
        mIClickSortListenerCallback = null;
        super.onDetach();
    }

    public interface IClickSortListener {

        void updateSort(String sort, String filterFile, String filterTime);
        void resetSort();
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialog;
    }
}