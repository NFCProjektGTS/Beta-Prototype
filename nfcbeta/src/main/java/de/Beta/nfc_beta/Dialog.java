package de.Beta.nfc_beta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;


/**
 * Created by Kern on 10.06.2014.
 */

public class Dialog {
    private static InterfaceUI iface;

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


                break;
        }

    }
}
