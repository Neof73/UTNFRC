package ar.edu.frc.utn.app;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRadio extends Fragment {
    private enum mediaStatus {LOADING, STARTED, STOPPED};
    private final static String stream = "http://radio.solumedia.com.ar:8200/;";
    private Button play;
    private ProgressBar progressBar;
    private static MediaPlayer mediaPlayer;
    private static boolean started = false;
    private static boolean prepared = false;
    private static boolean loading = false;
    private static String strLoading;
    private static String strPause;
    private static String strPlay;

    public FragmentRadio() {
        //NADA!
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (strLoading == null) {
            strLoading = getActivity().getResources().getString(R.string.loading);
            strPause = getActivity().getResources().getString(R.string.pause);
            strPlay = getActivity().getResources().getString(R.string.play);
        }
        play = (Button) getActivity().findViewById(R.id.play);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    LoadingMedia();
                } else {
                    if (started) {
                        mediaPlayer.pause();
                        setButton(mediaStatus.STOPPED);
                    } else {
                        mediaPlayer.start();
                        setButton(mediaStatus.STARTED);
                    }
                }

            }
        });

        if (!loading){
            if (started){
                setButton(mediaStatus.STARTED);
            } else {
                setButton(mediaStatus.STOPPED);
            }
        } else {
            setButton(mediaStatus.LOADING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.radio_layout, container, false);
        return fragment;
    }

    private void LoadingMedia() {
        if (mediaPlayer == null) {

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    prepared = true;
                    loading = false;
                    play.callOnClick();
                }
            });

            try {
                loading = true;
                setButton(mediaStatus.LOADING);
                mediaPlayer.setDataSource(stream);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
                loading = false;
                prepared = false;
            }
        }
    }

    private void setButton(mediaStatus status) {
        switch (status) {
            case LOADING: {
                started = false;
                play.setEnabled(false);
                play.setCompoundDrawablesWithIntrinsicBounds(R.drawable.loading, 0, 0, 0);
                play.setText(strLoading);
                progressBar.setVisibility(View.VISIBLE);
                break;
            }
            case STARTED: {
                started = true;
                play.setEnabled(true);
                play.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pause, 0, 0, 0);
                play.setText(strPause);
                progressBar.setVisibility(View.INVISIBLE);
                break;
            }
            case STOPPED: {
                started = false;
                play.setEnabled(true);
                play.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play, 0, 0, 0);
                play.setText(strPlay);
                progressBar.setVisibility(View.INVISIBLE);
                break;
            }
            default: {
                started = false;
                play.setEnabled(true);
                play.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play, 0, 0, 0);
                play.setText(strPlay);
                progressBar.setVisibility(View.INVISIBLE);
                break;
            }
        }
    }
}
