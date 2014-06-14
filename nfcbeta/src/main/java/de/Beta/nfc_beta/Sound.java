package de.Beta.nfc_beta;

import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Clemens on 13.06.2014.
 */
public class Sound {
    SoundPool mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    // Variable streamid, ID des Sounds der gerade abgespielt wird
    private int streamID;

    // Methoden
    // Methode zum Sound abspielen, String path ist der Dateipfad
    public void soundAbspielen(String path) {
        int soundID = mSoundPool.load(path, 1);
        int streamID = mSoundPool.play(soundID, 1, 1, 1, 0, 1);
    }
    // Methode zum Sound pausieren
    public void soundPausieren() {
        mSoundPool.pause(streamID);
    }
    // Methode zum Sound weiterausgeben
    public void soundFortsetzen() {
        mSoundPool.resume(streamID);
    }

    public int getStreamID() {
        return (streamID);
    }

    //setter getter Methoden für alle Variablen
    public void setStreamID(int streamID) {
        this.streamID = streamID;
    }
}
