package com.example.team7_app.File;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7_app.R;
import com.example.team7_app.my_interface.IClickItemOptionListener;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private List<File> file;
    private IClickItemOptionListener iClickItemOptionListener;
    private Context context;

    public FileAdapter(List<File> file, IClickItemOptionListener iClickItemOptionListener, Context context) {
        this.file = file;
        this.iClickItemOptionListener = iClickItemOptionListener;
        this.context = context;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        File selectedFile = file.get(position);

        holder.tvName.setText(selectedFile.getName());
        holder.tvName.setSelected(true);

        int items = 0;
        if(selectedFile.isDirectory())
        {
            File[] files = selectedFile.listFiles();
            for(File singleFiles : files)
            {
                if(!singleFiles.isHidden())
                {
                    items++;
                }
            }
            holder.tvSize.setText(String.valueOf(items)+" Files");
        }
        else
        {
            SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
            Date lastModified = new Date(selectedFile.lastModified());
            holder.tvSize.setText(format.format(lastModified) + " | " + Formatter.formatShortFileSize(context, selectedFile.length()));
        }

        if(selectedFile.getName().endsWith(".jpeg"))
        {
            holder.ivLogo.setImageResource(R.drawable.ic_image);
        }
        else if(selectedFile.getName().endsWith(".jpg")){
            holder.ivLogo.setImageResource(R.drawable.ic_image);
        }
        else if(selectedFile.getName().endsWith(".png")){
            holder.ivLogo.setImageResource(R.drawable.ic_image);
        }
        else if(selectedFile.getName().endsWith(".pdf")){
            holder.ivLogo.setImageResource(R.drawable.ic_pdf);
        }
        else if(selectedFile.getName().endsWith(".doc")){
            holder.ivLogo.setImageResource(R.drawable.ic_docs);
        }
        else if(selectedFile.getName().endsWith(".mp3")){
            holder.ivLogo.setImageResource(R.drawable.ic_music);
        }
        else if(selectedFile.getName().endsWith(".wav")){
            holder.ivLogo.setImageResource(R.drawable.ic_music);
        }
        else if(selectedFile.getName().endsWith(".mp4")){
            holder.ivLogo.setImageResource(R.drawable.ic_play);
        }
        else if(selectedFile.getName().endsWith(".apk")){
            holder.ivLogo.setImageResource(R.drawable.ic_android);
        }
        else{
            holder.ivLogo.setImageResource(R.drawable.folder);
        }

        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemOptionListener.onClickItemOption(selectedFile);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (file != null) {
            return file.size();
        }
        return 0;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvSize;
        public CardView item;
        public ImageView ivLogo;
        public ImageButton btnOption;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_item_tv_name);
            tvSize = itemView.findViewById(R.id.item_item_tv_info);
            item = itemView.findViewById(R.id.item_item_cv_item);
            ivLogo = itemView.findViewById(R.id.item_item_iv_logo);
            btnOption = itemView.findViewById(R.id.item_item_btn_option);
        }
    }

    // Lay kich thuoc file
    public static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}