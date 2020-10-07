package com.example.musiplay;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class horizontalSongAdapter extends RecyclerView.Adapter<horizontalSongAdapter.ViewHolder> {

    private List<horizontalSongScrollModel> horizontalSongScrollModelList;

    public horizontalSongAdapter(List<horizontalSongScrollModel> horizontalSongScrollModelList) {
        this.horizontalSongScrollModelList = horizontalSongScrollModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horixontal_scroll_layout_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int resource = horizontalSongScrollModelList.get(position).getSongImage();
        String title = horizontalSongScrollModelList.get(position).getSongName();

        holder.setSongImage(resource);
        holder.setSongName(title);
    }

    @Override
    public int getItemCount() {
        if (horizontalSongScrollModelList.size()> 8){
            return 8;
        }else{

        }return horizontalSongScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView songImage;
        private TextView songName;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            songImage = itemView.findViewById(R.id.song_image);
            songName = itemView.findViewById(R.id.song_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),OnlineSongActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        private  void setSongImage(int resource) {
            songImage.setImageResource(resource);
        }

        private void setSongName(String title){
            songName.setText(title);
        }
        }
    }

