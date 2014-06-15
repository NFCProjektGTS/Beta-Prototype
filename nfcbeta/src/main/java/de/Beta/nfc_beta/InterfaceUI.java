package de.Beta.nfc_beta;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by Kern on 03.06.2014.
 */
public class InterfaceUI {
    Activity mContext;
    NFCFramework framework = MainActivity.framework;
    DebugFragment df = MainActivity.df;

    InterfaceUI(Activity c) {
        mContext = c;

    }


    void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
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
        //framework.setPayload(OpCodes.OPC_SILENT); braucht man nicht
        framework.createWriteNdef(NdefCreator.muteMessage());
        framework.enableWrite();
        printDebugInfo("Schreibe Stummschalten");
    }

    public void writeKontakt(String payload) {
        framework.setPayload(payload);
        if (!framework.getPayload().equals("")) {
            framework.createWriteNdef(NdefCreator.vCard(framework.getPayload()));
            framework.enableWrite();
        }
        //mContext.startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE), 1);
        printDebugInfo("Schreibe Kontakt");
    }

    public void activateNFC() {
        mContext.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET));
    }

    public void writePicture() {
        //select image dialog

    }

    public void writeSound() {
        // first get soundd
        // second create NdefMessage with sounddata
        // third setPayload
        //framework.setPayload(Operations.OPC_SOUND_01);
        //framework.createWriteNdef(NdefCreator.Sound01Message());
        //framework.enableWrite();
    }


    public void writeText(String s) {
        printDebugInfo("Schreibe Text: "+s);
        framework.setPayload(s);
        framework.createWriteNdef(NdefCreator.fromText(s, "de_DE"));
        framework.enableWrite();
        //TODO NFC ZEUG
    }
    public void writeURL(String s) {
        framework.installService();
        printDebugInfo("Schreibe URL: " + s);
        //TODO NFC ZEUG
    }

    public void chooseContact() {
        mContext.startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE), 1);
        //TODO KP WIE MAN DAS MACHT
    }

    public void choosePicture() {
    }

    public void chooseSound() {

    }
}

