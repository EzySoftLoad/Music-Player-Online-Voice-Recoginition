package com.example.musiplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.io.File;
import java.lang.String;
import java.lang.Thread;

import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static android.speech.SpeechRecognizer.*;

public class SongsActivity extends AppCompatActivity {
    private RelativeLayout parentRelativeLayout;
    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;
    private String Keeper = "";


    private ImageView pausePlayBtn, nextBtn, previousBtn;
    private TextView songNameTxt;
    private SeekBar seekBar;

    private ImageView imageView;
    private RelativeLayout lowerRelativeLayout;
    private Button VoiceEnableBtn;
    private String mode = "ON";

    private TextView currentTime;
    private TextView totalTime;
    public static MediaPlayer mymediaplayer;
    private int position;
    private ArrayList<File> mySongs;
    private String mSongName;
    private Thread updateseekbar;
    private ImageView songImage;
    private Handler handler = new Handler();




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_more, menu );
        return true;
    }



    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        CheckVoiceCommandPermission();

        pausePlayBtn = findViewById( R.id.play_pause_btn );
        nextBtn = findViewById( R.id.next_btn );
        previousBtn = findViewById( R.id.previous_btn );
        imageView = findViewById( R.id.logo );


        lowerRelativeLayout = findViewById( R.id.lower );
        VoiceEnableBtn = findViewById( R.id.voice_enable_btn );
        songNameTxt = findViewById( R.id.songName );
        songImage=findViewById(R.id.logo);

        seekBar = (SeekBar) findViewById( R.id.seekbar );
        seekBar.setMax(100);

        updateseekbar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mymediaplayer.getDuration();
                int currentPostition = 0;


                while (currentPostition < totalDuration) {
                    try {
                        sleep( 5000 );
                        currentPostition = mymediaplayer.getCurrentPosition();
                        seekBar.setProgress( currentPostition );


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        parentRelativeLayout = findViewById( R.id.parentRelativeLayout );
        speechRecognizer = createSpeechRecognizer( SongsActivity.this );
        speechRecognizerIntent = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        speechRecognizerIntent.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM );
        speechRecognizerIntent.putExtra( RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault() );


        ValidateRecieveValuesAndStartPlaying();
        imageView.setBackgroundResource( R.drawable.songicon );


        speechRecognizer.setRecognitionListener( new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matchesFound = results.getStringArrayList( SpeechRecognizer.RESULTS_RECOGNITION );

                if (matchesFound != null) {
                    if (mode.equals( "ON" )) {
                        Keeper = matchesFound.get( 0 );

                        if (Keeper.equals( "stop the song" )) {
                            playpauseSong();
                            Toast.makeText( SongsActivity.this, "" + Keeper, Toast.LENGTH_LONG ).show();
                        } else if (Keeper.equals( "play the song" )) {
                            playpauseSong();
                            Toast.makeText( SongsActivity.this, "" + Keeper, Toast.LENGTH_LONG ).show();
                        } else if (Keeper.equals( "play next song" )) {
                            playNextSong();
                            Toast.makeText( SongsActivity.this, "" + Keeper, Toast.LENGTH_LONG ).show();
                        } else if (Keeper.equals( "play previous song" )) {
                            playPreviousSong();
                            Toast.makeText( SongsActivity.this, "" + Keeper, Toast.LENGTH_LONG ).show();
                        }


                    }


                }

            }


            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        } );


        parentRelativeLayout.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        speechRecognizer.startListening( speechRecognizerIntent );
                        Keeper = "";
                        break;


                    case MotionEvent.ACTION_UP:
                        speechRecognizer.stopListening();
                        break;
                    default:
                        throw new IllegalStateException( "Unexpected value: " + motionEvent.getAction() );
                }

                return false;
            }
        } );


        VoiceEnableBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode.equals( "ON" )) {
                    mode = "OFf";
                    VoiceEnableBtn.setText( "voice enable mode ~ OFF" );
                    lowerRelativeLayout.setVisibility( View.VISIBLE );
                } else {
                    mode = "ON";
                    VoiceEnableBtn.setText( "voice enable mode ~ ON" );
                    lowerRelativeLayout.setVisibility( View.GONE );
                }
            }
        } );


        pausePlayBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setMax( mymediaplayer.getDuration() );
                playpauseSong();

            }
        } );

        previousBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mymediaplayer.getCurrentPosition()>0) {
                    playPreviousSong();
                }

            }
        } );

        nextBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mymediaplayer.getCurrentPosition() > 0) {
                    playNextSong();
                }

            }
        } );
    }




    private void ValidateRecieveValuesAndStartPlaying() {
        if (mymediaplayer != null) {
            mymediaplayer.stop();
            mymediaplayer.reset();
            mymediaplayer.release();
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        mySongs = (ArrayList) bundle.getParcelableArrayList( "song" );
        mSongName = mySongs.get( position ).getName();
        String songName = intent.getStringExtra( "name" );



        songNameTxt.setText( songName );
        songNameTxt.setSelected( true );



        position = bundle.getInt( "position", 0 );
        Uri uri = Uri.parse( mySongs.get( position ).toString() );


        mymediaplayer = MediaPlayer.create( SongsActivity.this, uri );
        mymediaplayer.start();


        seekBar.setMax( mymediaplayer.getDuration() );
        updateseekbar.start();


        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mymediaplayer.seekTo( seekBar.getProgress() );


            }
        } );


    }


    private void CheckVoiceCommandPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission( SongsActivity.this, Manifest.permission.RECORD_AUDIO ) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse( "package:" + getPackageName() ) );
                startActivity( intent );
                finish();


            }
        }
    }


    private void playpauseSong() {
        imageView.setBackgroundResource( R.drawable.musicc_play );

        if (mymediaplayer.isPlaying()) {
            pausePlayBtn.setImageResource( R.drawable.play );
            mymediaplayer.pause();
        } else {
            pausePlayBtn.setImageResource( R.drawable.pause );
            mymediaplayer.start();

            imageView.setBackgroundResource( R.drawable.musicc_play);
        }
    }

    private void playNextSong() {
        mymediaplayer.pause();
        mymediaplayer.stop();
        mymediaplayer.reset();
        mymediaplayer.release();

        position = ((position + 1) % mySongs.size());

        Uri uri = Uri.parse( mySongs.get( position ).toString() );
        mymediaplayer = MediaPlayer.create( SongsActivity.this, uri );

        mSongName = mySongs.get( position ).toString();
        songNameTxt.setText( mSongName );
        mymediaplayer.start();


        imageView.setBackgroundResource( R.drawable.musicc_play );

        if (mymediaplayer.isPlaying()) {
            pausePlayBtn.setBackgroundResource( R.drawable.pause );
        } else {
            pausePlayBtn.setBackgroundResource( R.drawable.play );

            imageView.setBackgroundResource( R.drawable.play_pause );
        }


    }


    private void playPreviousSong() {
        mymediaplayer.pause();
        mymediaplayer.stop();
        mymediaplayer.reset();
        mymediaplayer.release();

        position = ((position - 1) < 0 ? (mySongs.size() - 1) : (position - 1));

        Uri uri = Uri.parse( mySongs.get( position ).toString() );
        mymediaplayer = MediaPlayer.create( SongsActivity.this, uri );


        mSongName = mySongs.get( position ).toString();
        songNameTxt.setText( mSongName );
        songImage.setImageResource(R.drawable.icon);
        mymediaplayer.start();


        imageView.setBackgroundResource( R.drawable.play_pause);

        if (mymediaplayer.isPlaying()) {
            pausePlayBtn.setBackgroundResource( R.drawable.pause );
        } else {
            pausePlayBtn.setBackgroundResource( R.drawable.play );

            imageView.setBackgroundResource( R.drawable.play_pause );
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected( item );
    }



}

