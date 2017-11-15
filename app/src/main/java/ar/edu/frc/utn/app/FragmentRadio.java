package ar.edu.frc.utn.app;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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
    private final static int NOTIFICATION_ID = 1234;

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
        setNotification(status);

    }

    private void setNotification(mediaStatus status)
    {
        switch (status) {
            case STARTED: {
                getActivity().registerReceiver(stopServiceReceiver, new IntentFilter("myFilter"));
                PendingIntent contentIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent("myFilter"), PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.radio)
                        .setContentTitle(getString(R.string.radionotifytitle))
                        .setContentText(getString(R.string.radionotifytext))
                        .setContentIntent(contentIntent);
                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
                break;
            }
            default: {
                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(NOTIFICATION_ID);
                break;
            }
        }
    }

    //We need to declare the receiver with onReceive function as below
    protected BroadcastReceiver stopServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            play.callOnClick();
        }
    };
}
