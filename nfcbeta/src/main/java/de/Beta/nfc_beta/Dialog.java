package de.Beta.nfc_beta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;


/**
 * Created by Kern on 10.06.2014.
 */

public class Dialog {
    static InterfaceUI iface;

    Dialog(final Activity mContext, int id) {
        iface = MainActivity.iface;
        switch (id) {
            case 0:
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.dialog_nfc_title)
                        .setMessage(R.string.dialog_nfc)
                        .setPositiveButton(R.string.dialog_nfc_on, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                iface.activateNFC();
                            }
                        })
                        .setNegativeButton(R.string.dialog_nfc_off, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                iface.printDebugWarn("NFC wurde nicht Aktiviert");
                            }
                        })
                        .setIcon(android.R.drawable.ic_notification_clear_all)
                        .show();
                break;
            case 1:
                WritingAnimationFragment animateFragment = new WritingAnimationFragment();

                MainActivity.fragmentManager.beginTransaction()
                        .replace(R.id.animation_frame, animateFragment)

                        .commit();
                //animateFragment.loadNfcAnimation();   TODO
                //animateFragment.startAnimation();     TODO
                AlertDialog dialog = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_DARK)
                        .setTitle("NFC Tag beschreiben")
                                //.setView(mContext.getLayoutInflater().inflate(R.layout.dialog_wanimation, null))
                        .setCancelable(false)
                        .show();
                //animateFragment.startAnimation();


                // FrameLayout fl = (FrameLayout) mContext.findViewById(android.R.id.custom);
                // fl.addView(myView, new FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));


                break;
        }

    }
}
