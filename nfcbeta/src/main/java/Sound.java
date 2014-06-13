import android.media.SoundPool;

/**
 * Created by Clemens on 13.06.2014.
 */
public class Sound {
    // Variable streamid, ID des Sounds der gerade abgespielt wird
    private int streamID;

    // Methoden
    // Methode zum Sound abspielen, String path ist der Dateipfad
    public void soundAbspielen(String path) {
        int soundID = SoundPool.load(path, 1);
        int streamID = SoundPool.play(soundID, 1.0, 1.0, 1, 0, 1);
    }
    // Methode zum Sound pausieren
    public void soundPausieren() {
        SoundPool.pause(streamID);
    }
    // Methode zum Sound weiterausgeben
    public void soundFortsetzen() {
        SoundPool.resume(streamID);
    }

    //setter getter Methoden für alle Variablen
    public void setStreamID(int streamID) {
        this.streamID = streamID;
    }
    public int getStreamID() {
        return (streamID);
    }
}
