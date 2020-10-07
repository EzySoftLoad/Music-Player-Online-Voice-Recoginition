package com.example.musiplay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class ByPassActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean playpause;
    private Button button_p1;
    private Button button_p2;
    private boolean initialstage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_pass);


        button_p1 = findViewById(R.id.latest_p1);
        button_p2 = findViewById(R.id.latest_p2);

        mediaPlayer = new MediaPlayer();


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);



        ///////////for the best Songs -Button P1 start
        button_p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playpause) {

                    button_p1.setText("");

                    if (initialstage) {
                        new Player().execute("https://firebasestorage.googleapis.com/v0/b/musiplay-79067.appspot.com/o/Duniyaa%20(From%20'Luka%20Chuppi').mp3?alt=media&token=f981d8d7-b5f9-4e4a-ad38-65ba893b1e40");

                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();

                    }

                    playpause = true;
                } else {
                    button_p1.setText("");


                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                    playpause = false;
                }
            }
        });


////////////////////////////////

        button_p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playpause) {

                    button_p2.setText("");

                    if (initialstage) {
                        new Player().execute("https://firebasestorage.googleapis.com/v0/b/musiplay-79067.appspot.com/o/Tera%20Ban%20Jaunga%20Lyrics%20_%20Tulsi%20Kumar%20_%20Akhil%20Sachdeva%20_%20Kabir%20Singh%20_%20T%20Lyrics.mp3?alt=media&token=761c0a92-7fc2-4635-8ab4-6cc29340dcde");

                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();

                    }

                    playpause = true;
                } else {
                    button_p2.setText("");


                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                    playpause = false;
                }
            }
        });
    }

////////////////////////////////


    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;

        }
    }

    class Player extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        initialstage = true;

                        playpause = false;
                        button_p1.setText("");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;

            } catch (IOException e) {

                Log.e("MusiPlay", e.getMessage());
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog.isShowing()) {

                progressDialog.cancel();
            }
            mediaPlayer.start();
            initialstage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setTitle("Please Wait...");
            progressDialog.setMessage("Selected song is on Buffering...");
            progressDialog.show();
        }
    }
    ///////////for the best Songs -Button P1 finish


}
