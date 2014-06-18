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


    }

    public static void initImage(NdefRecord msg) {
        byte[] payload = msg.getPayload();

    }

    public static void initText(NdefRecord msg) {

        TextFragment t = TextFragment.newInstance(new String(msg.getPayload()));
        MainActivity.fragmentManager.beginTransaction().replace(R.id.container,  t).commit();
    }
}
