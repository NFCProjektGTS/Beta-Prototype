package de.Beta.nfc_beta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.widget.Toast;
import de.Beta.nfc_beta.DebugFragment;
import de.Beta.nfc_beta.DebugFragment;

/**
 * Created by Kern on 03.06.2014.
 */
public class InterfaceUI {
    Context mContext;
    NFCFramework framework= MainActivity.framework;
    DebugFragment df = MainActivity.df;

    InterfaceUI(Context c) {
        mContext = c;
    }


    void showToast(String text) {
        Toast.makeText(mContext,text, Toast.LENGTH_LONG).show();
    }

    public void printDebugInfo(String text) {
        df.addLine(0, text);
    }

    public void printDebugWarn(String text) {
        df.addLine(1, text);
    }

    public void printDebugError(String text) {
        df.addLine(2, text);
    }



    public void writeStummschalten() {
        framework.setPayload(Operations.OPC_SILENT);
        framework.createWriteNdef(NdefCreator.muteMessage());
        framework.enableWrite();
        printDebugInfo("Schreibe Stummschalten");
    }
    public  void writeKontakt() {
       // mContext.startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE), 1);

        printDebugInfo("Schreibe Kontakt");
    }

    public void activateNFC() {
        mContext.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET));
    }

    public void writePicture() {
    }

    public void writeSound() {
        framework.setPayload(Operations.OPC_SOUND_01);
        framework.createWriteNdef(NdefCreator.Sound01Message());
        framework.enableWrite();
    }
}

