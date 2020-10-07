package com.example.musiplay;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineModeFragment extends Fragment {


    public OnlineModeFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_online_mode, container, false);


        List<horizontalSongScrollModel> horizontalSongScrollModelList = new ArrayList<>();

        horizontalSongScrollModelList.add(new horizontalSongScrollModel(R.drawable.p1,"Duniya"));
        horizontalSongScrollModelList.add(new horizontalSongScrollModel(R.drawable.p2,"Chhod Diya"));
        horizontalSongScrollModelList.add(new horizontalSongScrollModel(R.drawable.p3,"Dheere Dheere"));
        horizontalSongScrollModelList.add(new horizontalSongScrollModel(R.drawable.p4,"Bhula diya"));
        horizontalSongScrollModelList.add(new horizontalSongScrollModel(R.drawable.p5,"Tu pyar"));
        horizontalSongScrollModelList.add(new horizontalSongScrollModel(R.drawable.p6,"Filhaal"));
        horizontalSongScrollModelList.add(new horizontalSongScrollModel(R.drawable.p7,"Rehna Tu Pal Pal"));
        horizontalSongScrollModelList.add(new horizontalSongScrollModel(R.drawable.p5,"Aaj Jid"));


        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager testingLinearLayoutManager = new LinearLayoutManager(getContext());
        testingLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(testingLinearLayoutManager);


        List<OnlineModeModel>onlineModeModelList = new ArrayList<>();
        onlineModeModelList.add(new OnlineModeModel(0,"Latest Songs",horizontalSongScrollModelList));
        onlineModeModelList.add(new OnlineModeModel(0,"Made For You",horizontalSongScrollModelList));
        onlineModeModelList.add(new OnlineModeModel(0,"Latest Album",horizontalSongScrollModelList));
        onlineModeModelList.add(new OnlineModeModel(1,R.drawable.kbs_logo));
        onlineModeModelList.add(new OnlineModeModel(0,"Popular Album",horizontalSongScrollModelList));
        onlineModeModelList.add(new OnlineModeModel(0,"Trending Artist",horizontalSongScrollModelList));
        onlineModeModelList.add(new OnlineModeModel(0,"Trending Playlist",horizontalSongScrollModelList));
        onlineModeModelList.add(new OnlineModeModel(0,"Party Songs",horizontalSongScrollModelList));
        onlineModeModelList.add(new OnlineModeModel(0,"Devotional Songs",horizontalSongScrollModelList));
        onlineModeModelList.add(new OnlineModeModel(0,"Latest India Pop Songs",horizontalSongScrollModelList));
        onlineModeModelList.add(new OnlineModeModel(0,"All about 90s",horizontalSongScrollModelList));

        OnlineModeAdapter adapter = new OnlineModeAdapter(onlineModeModelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }

}
