package de.Beta.nfc_beta;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MediaPlaybackActivity extends Activity implements OnClickListener {
    SeekBar seek_bar;
    Button play_button, pause_button;
    MediaPlayer player;
    TextView text_shown;
    Handler seekHandler = new Handler();
    Runnable run = new Runnable() {

        @Override
        public void run() {
            seekUpdation();
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_showsound);
        Intent i = getIntent();

        String file = i.getStringExtra(SoundFragment.MESSAGE);

        Init(file);
        seekUpdation();
    }

    public void Init(String file) {
        seek_bar = (SeekBar) findViewById(R.id.seek_bar);
        play_button = (Button) findViewById(R.id.play_button);
        pause_button = (Button) findViewById(R.id.pause_button);
        text_shown = (TextView) findViewById(R.id.text_shown);
        play_button.setOnClickListener(this);
        pause_button.setOnClickListener(this);

        //Uri path = Uri.parse("file:///android_assets/sounds/door.mp3");
        player = MediaPlayer.create(this, path);
        seek_bar.setMax(player.getDuration());
    }

    public void seekUpdation() {

        seek_bar.setProgress(player.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    @Override
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