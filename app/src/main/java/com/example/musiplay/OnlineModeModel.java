package com.example.musiplay;

import java.util.List;

public class OnlineModeModel  {

    public static final int HORIZONTAL_SONG_SCROLL_VIEW = 0;
    public static final int STRIP_AD_BANNER =1;


    private int type;

    //////Horizontal song view
    private  String title;
    private List<horizontalSongScrollModel> horizontalSongScrollModelList;
    public OnlineModeModel(int type, String title, List<horizontalSongScrollModel> horizontalSongScrollModelList) {
        this.type = type;
        this.title = title;
        this.horizontalSongScrollModelList = horizontalSongScrollModelList;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<horizontalSongScrollModel> getHorizontalSongScrollModelList() {
        return horizontalSongScrollModelList;
    }
    public void horizontalSongScrollModelList(List<horizontalSongScrollModel> horizontalSongScrollModelList) {
        this.horizontalSongScrollModelList = horizontalSongScrollModelList;
    }
    /////// //////Horizontal song view

    /////////////////Strip ad banner
    private int resource;

    public OnlineModeModel(int type, int resource) {
        this.type = type;
        this.resource = resource;
    }
    public int getResource() {
        return resource;
    }
    public void setResource(int resource) {
        this.resource = resource;
    }


    /////////////////strip as banner



}
