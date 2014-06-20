
package de.Beta.nfc_beta;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Kern on 16.06.2014.
 */

public class SoundFragment extends Fragment implements View.OnClickListener {

    public static final String MESSAGE = "de.Beta.nfc_beta.FILEMESSAGE";
    private static final String SOUND = "section_number";
    private static String soundpath;
    private View view;
    private SeekBar seek_bar;
    private Button play_button, pause_button;
    private MediaPlayer player;
    private TextView text_shown;
    private Handler seekHandler = new Handler();
    private Runnable run = new Runnable() {

        @Override
        public void run() {
            seekUpdation();
        }
    };

    public SoundFragment() {
    }

    public static SoundFragment newInstance(String sound) {
        SoundFragment fragment = new SoundFragment();
        Bundle args = new Bundle();
        soundpath = sound;
        args.putString(SOUND, sound);
        fragment.setArguments(args);

        return fragment;
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
    }


    public void seekUpdation() {
        seek_bar.setProgress(player.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    public void Init(String file) {
        seek_bar = (SeekBar) view.findViewById(R.id.seek_bar);
        play_button = (Button) view.findViewById(R.id.play_button);
        pause_button = (Button) view.findViewById(R.id.pause_button);
        text_shown = (TextView) view.findViewById(R.id.text_shown);
        play_button.setOnClickListener(this);
        pause_button.setOnClickListener(this);
        AssetFileDescriptor afd;
        try {
            afd = getActivity().getAssets().openFd("sounds/" + file);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.setLooping(true);
            //player = MediaPlayer.create(this, path);
            seek_bar.setMax(player.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_button:
                text_shown.setText("Playing...");
                player.start();
                break;
            case R.id.pause_button:
                player.pause();
                text_shown.setText("Paused...");
        }

    }

}



