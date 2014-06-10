package de.Beta.nfc_beta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Created by Kern on 10.06.2014.
 */

public class Dialog {
    static InterfaceUI iface;
    Dialog(final Context mContext,int id) {
        iface = new InterfaceUI(mContext);
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
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.dialog_write)
                        .setItems(R.array.dialog_write_opts, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        iface.writeKontakt();
                                        break;
                                    case 1:
                                        iface.writeStummschalten();
                                        break;
                                    case 2:
                                        iface.writePicture();
                                        break;
                                    case 3:
                                        iface.writeSound();
                                        break;
                                }
                            }
                        })
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .show();
                break;
        }

    }



}
