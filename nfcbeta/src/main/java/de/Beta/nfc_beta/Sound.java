package de.Beta.nfc_beta;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

/**
 * Created by Clemens on 13.06.2014.
 */
public class Sound {
    AssetManager am;
    private int streamID;
    SoundPool sp;
    Sound(Context ctx){
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        am = ctx.getAssets();
    }


    // Methoden
    // Methode zum Sound abspielen, String filename ist der Dateiname
    public void soundAbspielen(String filename) {
        int soundID = 0;
        try {

            soundID = sp.load(am.openFd("sounds/"+filename), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int streamID = sp.play(soundID, 1, 1, 1, 0, 1);
    }
    // Methode zum Sound pausieren
    public void soundPausieren() {
        sp.pause(streamID);
    }
    // Methode zum Sound weiterausgeben
    public void soundFortsetzen() {
        sp.resume(streamID);
    }

    public int getStreamID() {
        return (streamID);
    }

    //setter getter Methoden für alle Variablen
    public void setStreamID(int streamID) {
        this.streamID = streamID;
    }
}
