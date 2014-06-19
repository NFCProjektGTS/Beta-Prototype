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
    SoundPool sp;
    private int streamID;

    Sound(Context ctx){
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        am = ctx.getAssets();

    }

   Sound(){};


    // Methoden
    // Methode zum Sound abspielen, String filename ist der Dateiname
    public void soundAbspielen(String filename) {

        try {

            final int soundID = sp.load(am.openFd("sounds/"+filename), 1);
            SoundPool.OnLoadCompleteListener loadcop =new SoundPool.OnLoadCompleteListener(){


                @Override
                public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                    streamID = sp.play(soundID, 1, 1, 1, 0, 1);
                }
            };
            sp.setOnLoadCompleteListener(loadcop);
        } catch (IOException e) {
            e.printStackTrace();
        }


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
