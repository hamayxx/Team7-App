package com.example.team7_app.File;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7_app.R;

public class FileViewHolder extends RecyclerView.ViewHolder{
    public TextView tvName, tvSize;
    public CardView item;
    public ImageView ivLogo;
    public Button btnOption;

    public FileViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName= itemView.findViewById(R.id.item_item_tv_name);
        tvSize= itemView.findViewById(R.id.item_item_tv_info);
        item= itemView.findViewById(R.id.item_item_cv_item);
        ivLogo=itemView.findViewById(R.id.item_item_iv_logo);
        btnOption=itemView.findViewById(R.id.item_item_btn_option);
    }
}