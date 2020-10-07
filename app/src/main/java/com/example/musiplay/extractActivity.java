package com.example.musiplay;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.annotation.NonNull;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;


public class extractActivity extends AppCompatActivity {
    private String[] itemAll;
    private ListView mSongsList;
    private SearchView searchView;
   private ArrayAdapter arrayAdapter;
   private BottomNavigationView bottomNavigationView;


    private ConstraintLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mSongsList = findViewById(R.id.songsList);

        frameLayout = findViewById(R.id.main_layout);


        bottomNavigationView= findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              switch (item.getItemId()) {
                  case R.id.navigation_album:
                      break;

                  case R.id.navigation_online:

                      break;

                  case R.id.navigation_video:
                      Intent videoIntent = new Intent(extractActivity.this, OnlineVideoActivity.class);
                      startActivity(videoIntent);

                      break;

                  case R.id.navigation_search:
                      break;

              }
                      return true;
            }
        });

        appExternalStorageStoragePermission();
    }


    public void appExternalStorageStoragePermission()
    {
        Dexter.withActivity(this)
                .withPermission( Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response)
                    {

                        displayAudioSongName();

                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response)
                    {

                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
                    {
                        token.continuePermissionRequest();

                    }
                }).check();
    }


    public ArrayList<File> readOnlyAudioSongs(File file)
    {

        ArrayList<File> arrayList = new ArrayList<>();


        File[] allFiles = file.listFiles();

        assert allFiles != null;
        for (File individualFile : allFiles)
            if (individualFile.isDirectory())
            {
                arrayList.addAll( readOnlyAudioSongs( individualFile ) );

            }
            else
            {
                if (individualFile.getName().endsWith(".mp3") || individualFile.getName().endsWith(".MP3") ) {
                    arrayList.add( individualFile );
                }
            }
        return arrayList;
    }


    private void  displayAudioSongName()
    {
        final ArrayList<File> audioSongs = readOnlyAudioSongs( Environment.getExternalStorageDirectory());


        itemAll = new String[audioSongs.size()];

        for ( int songCounter=0 ; songCounter<audioSongs.size();songCounter++)
        {

            itemAll[songCounter] = audioSongs.get( songCounter ).getName().replace( ".mp3",".MP3" );
        }




        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(extractActivity.this,android.R.layout.simple_list_item_1,itemAll  );
        mSongsList.setAdapter( arrayAdapter );





        mSongsList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                String songName = mSongsList.getItemAtPosition( position ).toString();
                String songImage = mSongsList.getItemAtPosition(position).toString();


                Intent intent = new Intent( extractActivity.this, SongsActivity.class );
                intent.putExtra( "song" , audioSongs );
                intent.putExtra( "name" ,songName);
                intent.putExtra( "position",position );
                intent.putExtra("image",songImage);
                startActivity( intent );


            }
        });
    }






}
