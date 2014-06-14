package de.Beta.nfc_beta;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by Noli on 14.06.2014.
 */
public class Operations {
    public static void toggleSilent(Context ctx) {
        AudioManager am = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        if (am.getRingerMode() != AudioManager.RINGER_MODE_SILENT) {
            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else {
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }

    public static void initSound(Context ctx) {

    }

    public static void initImage(Context ctx) {

    }
}
