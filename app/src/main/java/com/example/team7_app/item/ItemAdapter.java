package com.example.team7_app.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7_app.R;
import com.example.team7_app.my_interface.IClickItemOptionListener;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> mItems;
    private IClickItemOptionListener iClickItemOptionListener;

    public ItemAdapter(List<Item> list, IClickItemOptionListener iClickItemOptionListener)
    {
        this.mItems = list;
        this.iClickItemOptionListener= iClickItemOptionListener;
    }

    public void setData(List<Item> list) {
        this.mItems = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mItems.get(position);
        if (item == null) {
            return;
        }

        holder.ivIcon.setBackgroundResource(item.getIconId());
        holder.tvName.setText(item.getName());
        holder.tvInfo.setText(item.getInfo());
        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemOptionListener.onClickItemOption(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivIcon;
        private TextView tvName;
        private TextView tvInfo;
        private ImageButton btnOption;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.item_item_iv_logo);
            tvName = itemView.findViewById(R.id.item_item_tv_name);
            tvInfo = itemView.findViewById(R.id.item_item_tv_info);
            btnOption = itemView.findViewById(R.id.item_item_btn_option);
        }
    }
}
