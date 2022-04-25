package com.example.team7_app.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7_app.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context mContext;
    private List<Category> mCategories;

    public CategoryAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<Category> list) {
        this.mCategories = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mCategories.get(position);
        if (category == null) {
            return;
        }

        holder.tvTitle.setText(category.getTitle());
        holder.ivIcon.setBackgroundResource(category.getIconId());
        holder.tvStatus.setText(category.getStatus());
    }

    @Override
    public int getItemCount() {
        if (mCategories != null) {
            return mCategories.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView ivIcon;
        private TextView tvStatus;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.item_category_tv_title);
            ivIcon = itemView.findViewById(R.id.item_category_iv_icon);
            tvStatus = itemView.findViewById(R.id.item_category_tv_status);
        }
    }
}
