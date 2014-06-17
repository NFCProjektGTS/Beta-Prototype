package de.Beta.nfc_beta;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kern on 03.06.2014.
 */
public class InterfaceUI {
    private final int ACTIVITY_CHOOSE_FILE = 1;
    Sound msound = new Sound();
    Activity mContext;
    NFCFramework framework;
    DebugFragment df = MainActivity.df;

    InterfaceUI(Activity c) {
        mContext = c;
        MainActivity.framework = new NFCFramework(mContext, this);
        framework = MainActivity.framework;
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
        if (framework != null) {
            framework.createWriteNdef(NdefCreator.muteMessage());
            framework.enableWrite();
            printDebugInfo("Schreibe Stummschalten");
        }
    }

    public void writeKontakt(String payload) {
        if (framework != null) {
            framework.setPayload(payload);
            if (!framework.getPayload().equals("")) {
                framework.createWriteNdef(NdefCreator.vCard(framework.getPayload()));
                framework.enableWrite();
            }
            //mContext.startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE), 1);
            printDebugInfo("Schreibe Kontakt");
        }
    }

    public void activateNFC() {
        mContext.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET));
    }

    public void writePicture(String pictureFillName) {
        if (framework != null) {
            //select image dialog
        }
    }

    public void writeSound() {
        if (framework != null) {
            // first get soundd
            // second create NdefMessage with sounddata
            // third setPayload
            //framework.setPayload(Operations.OPC_SOUND_01);
            //framework.createWriteNdef(NdefCreator.Sound01Message());
            //framework.enableWrite();
        }
    }


    public void writeText(String s) {
        if (framework != null) {
            printDebugInfo("Schreibe Text: " + s);
            framework.setPayload(s);
            if (!framework.getPayload().equals("")) {
                framework.createWriteNdef(NdefCreator.fromText(framework.getPayload(), "de_DE"));
                framework.enableWrite();
            }
        }
    }

    public void writeURL(String s) {
        if (framework != null) {
            try {
                URL url = new URL(s);
                printDebugInfo("Schreibe URL: " + s);
                framework.setPayload(s);
                if (!framework.getPayload().equals("")) {
                    framework.createWriteNdef(NdefCreator.fromUrl(url));
                    framework.enableWrite();
                }
            } catch (MalformedURLException e) {
                printDebugError("URL: " + s + " malformed!");
            }
        }
    }

    public void chooseContact() {
        mContext.startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE), 2); //2 == RQS_PICK_CONTACT
    }

    public void choosePicture() {
        try {
            String[] ourPictures = mContext.getAssets().list("pictures");
            ((MainActivity) mContext ).wpf.setImageList(ourPictures);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // coose from ressources

    }

    // Sound Methoden
    public void chooseSound() {
        // coose from ressources

    }

    public void playSound() {

    }

    public void pauseSound() {

    }


}

