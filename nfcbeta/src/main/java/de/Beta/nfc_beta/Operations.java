package de.Beta.nfc_beta;

import android.content.Context;
import android.media.AudioManager;
import android.nfc.NdefRecord;

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

    public static void initSound(NdefRecord msg) {
        MainActivity.showSoundFragment(new String(msg.getPayload()));
        //TODO TOAST?
    }

    public static void initImage(NdefRecord msg) {
        MainActivity.showPictureFragment(new String(msg.getPayload()));
        //TODO TOAST?
    }

    public static void initText(NdefRecord msg) {
        MainActivity.showTextFragment(new String(msg.getPayload()));
        //TODO TOAST?
    }
}
