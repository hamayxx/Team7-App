package vn.uit.nt213.m21.team8.file_manager.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.uit.nt213.m21.team8.file_manager.R;
import vn.uit.nt213.m21.team8.file_manager.my_interface.IClickItemCategoryListener;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private List<Category> mCategories;
    private IClickItemCategoryListener iClickItemCategoryListener;
    private Context context;

    public CategoryAdapter(List<Category> list, IClickItemCategoryListener iClickItemCategoryListener, Context context)
    {
        this.mCategories=list;
        this.iClickItemCategoryListener= iClickItemCategoryListener;
        this.context = context;
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
        holder.cvLayout.setCardBackgroundColor(ContextCompat.getColor(context, category.getColorId()));
        holder.cvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemCategoryListener.onClickItemCategory(category);

            }
        });
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
        private CardView cvLayout;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.item_category_tv_title);
            ivIcon = itemView.findViewById(R.id.item_category_iv_icon);
            tvStatus = itemView.findViewById(R.id.item_category_tv_status);
            cvLayout = itemView.findViewById(R.id.item_category_cv_layout);
        }
    }
}
