package com.example.team7_app.File;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7_app.R;

import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileViewHolder> {
    private Context context;
    private List<File> file;

    public FileAdapter(Context context, List<File> file) {
        this.context = context;
        this.file = file;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.tvName.setText(file.get(position).getName());
        holder.tvName.setSelected(true);
        int items =0;
        if(file.get(position).isDirectory())
        {
            File[] files = file.get(position).listFiles();
            for(File singleFiles : files)
            {
                if(!singleFiles.isHidden())
                {
                    items++;
                }
            }
            holder.tvSize.setText(String.valueOf(items)+"Files");
        }
        else
        {
            holder.tvSize.setText(Formatter.formatShortFileSize(context, file.get(position).length()));
        }

        if(file.get(position).getName().endsWith(".jpeg"))
        {
            holder.ivLogo.setImageResource(R.drawable.ic_image);
        }
        else if(file.get(position).getName().endsWith(".jpg")){
            holder.ivLogo.setImageResource(R.drawable.ic_image);
        }
        else if(file.get(position).getName().endsWith(".png")){
            holder.ivLogo.setImageResource(R.drawable.ic_image);
        }
        else if(file.get(position).getName().endsWith(".pdf")){
            holder.ivLogo.setImageResource(R.drawable.ic_pdf);
        }
        else if(file.get(position).getName().endsWith(".doc")){
            holder.ivLogo.setImageResource(R.drawable.ic_docs);
        }
        else if(file.get(position).getName().endsWith(".mp3")){
            holder.ivLogo.setImageResource(R.drawable.ic_music);
        }
        else if(file.get(position).getName().endsWith(".wav")){
            holder.ivLogo.setImageResource(R.drawable.ic_music);
        }
        else if(file.get(position).getName().endsWith(".mp4")){
            holder.ivLogo.setImageResource(R.drawable.ic_play);
        }
        else if(file.get(position).getName().endsWith(".apk")){
            holder.ivLogo.setImageResource(R.drawable.ic_android);
        }
        else{
            holder.ivLogo.setImageResource(R.drawable.folder);
        }
    }

    @Override
    public int getItemCount() {
        return file.size();
    }
}