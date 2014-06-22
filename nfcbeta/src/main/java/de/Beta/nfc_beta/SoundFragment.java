
package de.Beta.nfc_beta;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import static android.media.MediaPlayer.OnPreparedListener;

/**
 * Created by Kern on 16.06.2014.
 */

public class SoundFragment extends Fragment implements View.OnClickListener {

    private static final String SOUND = "section_number";
    private static String soundpath;
    private View view;
    private SeekBar seek_bar;
    private Button play_button, pause_button;
    private MediaPlayer player;
    private Thread seekthread = new Thread(new Runnable() {
        public void run() {
            seekbarupdate();
        }
    });
    private TextView text_shown;
    private boolean toggleplay = false;
    private boolean ready = false;

    public static SoundFragment newInstance(String sound) {
        SoundFragment fragment = new SoundFragment();
        Bundle args = new Bundle();
        soundpath = sound;
        args.putString(SOUND, sound);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_showsound, container, false);
        Init(soundpath);

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);((MainActivity)activity).onSectionAttached(103);//SWITCH CASE FÜR SOUND
    }

    @Override
    public void onDetach() {
        super.onDetach();
        player.stop();
        player.reset();
        player.release();
        player = null;
    }

    private void seekbarupdate() {
        while (player != null && player.getCurrentPosition() < player.getDuration()) {
            {
                seek_bar.setProgress(player.getCurrentPosition());
                Message msg = new Message();
                int millis = player.getCurrentPosition();
                msg.obj = millis / 1000;

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Init(String file) {
        seek_bar = (SeekBar) view.findViewById(R.id.seek_bar);
        play_button = (Button) view.findViewById(R.id.play_button);
        pause_button = (Button) view.findViewById(R.id.replay_button);
        text_shown = (TextView) view.findViewById(R.id.text_shown);
        play_button.setOnClickListener(this);
        pause_button.setOnClickListener(this);
        AssetFileDescriptor afd;
        player = new MediaPlayer();
        player.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                ready = true;
                seekthread.start();
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play_button.setText("Play");
                toggleplay = !toggleplay;
            }
        });
        try {
            afd = getActivity().getAssets().openFd("sounds/" + file);

            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            //player = MediaPlayer.create(this, path);
            seek_bar.setMax(player.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (!fromUser) return;
                player.seekTo(progress);
            }
        });
    }

    public void onClick(View view) {
        if(ready){
            switch (view.getId()) {
            case R.id.play_button:
                if (!toggleplay) {
                    text_shown.setText("Playing: " + soundpath);
                    player.start();
                    play_button.setText("Pause");
                    toggleplay = !toggleplay;
                } else {
                    text_shown.setText("Paused: " + soundpath);
                    player.pause();
                    play_button.setText("Play");
                    toggleplay = !toggleplay;
                }
                break;
            case R.id.replay_button:
                player.pause();
                player.seekTo(1);
                play_button.setText("Play");
                toggleplay = false;
        }
    }
    }
}



