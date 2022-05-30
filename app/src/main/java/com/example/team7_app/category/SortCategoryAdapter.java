package com.example.team7_app.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.team7_app.R;

import java.util.List;

public class SortCategoryAdapter extends ArrayAdapter<SortCategory> {
    public SortCategoryAdapter(@NonNull Context context, int resource, @NonNull List<SortCategory> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sort_item_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.fm_sort_selected_tv_filetype);

        SortCategory category = this.getItem(position);
        if (category != null) {
            tvSelected.setText(category.getTypename());

        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sort_item_category, parent, false);
        TextView tvCategory = convertView.findViewById(R.id.fm_sort_category_tv_filetype);

        SortCategory category = this.getItem(position);
        if (category != null) {
            tvCategory.setText(category.getTypename());

        }
        return convertView;
    }
}
