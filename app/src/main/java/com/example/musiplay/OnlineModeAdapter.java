package com.example.musiplay;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnlineModeAdapter extends RecyclerView.Adapter {

    private List<OnlineModeModel> onlineModeModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public OnlineModeAdapter(List<OnlineModeModel> onlineModeModelList) {
        this.onlineModeModelList = onlineModeModelList;
        this.recycledViewPool = new RecyclerView.RecycledViewPool();
    }



    @Override
    public int getItemViewType(int position) {
        switch (onlineModeModelList.get(position).getType()) {
            case 0:
                return OnlineModeModel.HORIZONTAL_SONG_SCROLL_VIEW;

            case 1:
                return OnlineModeModel.STRIP_AD_BANNER;

            default:
                return -1;
        }
}


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case OnlineModeModel.HORIZONTAL_SONG_SCROLL_VIEW:
                View horizontalScollview = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new HorizontalScrollViewHolder(horizontalScollview);


            case OnlineModeModel.STRIP_AD_BANNER:
                View stripAdbanner = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_banner, parent, false);
                return new StripProductViewHolder(stripAdbanner);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (onlineModeModelList.get(position).getType()) {

            case OnlineModeModel.HORIZONTAL_SONG_SCROLL_VIEW:
                String horizontallayouttitle = onlineModeModelList.get(position).getTitle();
                List<horizontalSongScrollModel> horizontalproductscrollModelList = onlineModeModelList.get(position).getHorizontalSongScrollModelList();
                ((HorizontalScrollViewHolder) holder).setHorizontalSongViewlayout(horizontalproductscrollModelList, horizontallayouttitle);
                break;

            case OnlineModeModel.STRIP_AD_BANNER:
                int resource = onlineModeModelList.get(position).getResource();
                ((StripProductViewHolder) holder).setStripAd(resource);
                break;

            default:
                return;
        }

    }



    @Override
    public int getItemCount() {
        return onlineModeModelList.size();
    }

    public class HorizontalScrollViewHolder extends RecyclerView.ViewHolder {
        private TextView playListTitle;
        private Button playlistViewAll;
        private RecyclerView playlistRecyclerView;


        public HorizontalScrollViewHolder(@NonNull View itemView) {
            super(itemView);



            playListTitle = itemView.findViewById(R.id.playlist_title);
            playlistViewAll = itemView.findViewById(R.id.playlist_view_all);
            playlistRecyclerView = itemView.findViewById(R.id.playlist_recyclerview);
            playlistRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalSongViewlayout(List<horizontalSongScrollModel> horizontalSongScrollModelList, String title) {

            playListTitle.setText(title);

            if (horizontalSongScrollModelList.size() > 8) {
                playlistViewAll.setVisibility(View.VISIBLE);
                playlistViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewallIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                        itemView.getContext().startActivity(viewallIntent);
                    }
                });
            } else {
                playlistViewAll.setVisibility(View.INVISIBLE);
            }

            horizontalSongAdapter horizontalSongAdapter = new horizontalSongAdapter(horizontalSongScrollModelList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            playlistRecyclerView.setLayoutManager(linearLayoutManager);

            playlistRecyclerView.setAdapter(horizontalSongAdapter);
            horizontalSongAdapter.notifyDataSetChanged();

        }
    }


    public class StripProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView stripAdImage;

        public StripProductViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);
        }

        private void setStripAd(int resource) {
            stripAdImage.setImageResource(resource);
        }
    }
    }


