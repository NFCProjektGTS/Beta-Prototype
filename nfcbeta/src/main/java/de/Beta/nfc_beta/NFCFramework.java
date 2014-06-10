package de.Beta.nfc_beta;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Noli on 27.05.2014.
 */
public class NFCFramework {

    protected NfcAdapter mNfcAdapter;
    protected Activity caller;
    protected Tag wTAG;
    protected boolean WriteMode = false;
    protected InterfaceUI wai;
    protected boolean enabled = false;
    protected IntentFilter[] mTagFilters;
    protected NdefMessage[] mCurrentNdef;
    protected NdefMessage[] mWriteNdef;
    private String payload = "";

    NFCFramework(Activity caller, InterfaceUI wai) {
        this.caller = caller;
        this.wai = wai;
        this.wai.printDebugInfo("Initialzing NFC Framework");
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(caller);
        this.enabled = checkNFC();

        if (enabled) wai.printDebugInfo("Adapter found: " + mNfcAdapter.toString());

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        this.mTagFilters = new IntentFilter[]{tagDetected};


    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void installService() {
        if (enabled) {
            Intent activityIntent = new Intent(caller, caller.getClass());
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent intent = PendingIntent.getActivity(caller, 0,
                    activityIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            mNfcAdapter.enableForegroundDispatch(caller, intent, mTagFilters, null);
        }
    }

    public void uninstallService() {
        if (enabled) {
            mNfcAdapter.disableForegroundDispatch(caller);
        }
    }


    public boolean checkNFC() {
        if (mNfcAdapter != null) {
            if (!mNfcAdapter.isEnabled()) {
                wai.printDebugInfo("NFC is disabled");
                wai.printDebugInfo("Opening NFC Activation Dialog");
                new Dialog(caller,0);
                if (mNfcAdapter.isEnabled()) {
                    return true;
                }
            }
            return true;
        } else {
           wai.showToast("NFC Hardware nicht gefunden");

        }
        return false;
    }

    public void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            this.wai.printDebugInfo("Tag Discovered");
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (!WriteMode) {
                this.wai.printDebugInfo("Reading");
                if (rawMsgs != null) {
                    msgs = new NdefMessage[rawMsgs.length];
                    for (int i = 0; i < rawMsgs.length; i++) {
                        msgs[i] = (NdefMessage) rawMsgs[i];
                    }
                } else {
                    msgs = RawNDEFContent(intent);

                }
                mCurrentNdef = msgs;
                operate(mCurrentNdef);
                printTag(mCurrentNdef);
            } else {
                this.wai.printDebugInfo("Writing");
                wTAG = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                if (wTAG != null && mWriteNdef[0] != null) {
                    wai.printDebugInfo(OnTagWriteListener.onTagWrite(writeTag(wTAG, mWriteNdef[0])));
                }
            }
        }

    }


    public byte[] rawTagData(Parcelable parc) {
        StringBuilder s = new StringBuilder();
        Tag tag = (Tag) parc;
        byte[] id = tag.getId();
        s.append("UID In Hex: ").append(Utils.convertByteArrayToHexString(id)).append("\n");
        s.append("UID In Dec: ").append(Utils.convertByteArrayToDecimal(id)).append("\n\n");

        String prefix = "android.nfc.tech.";
        s.append("Technologies: ");
        for (String tech : tag.getTechList()) {
            s.append(tech.substring(prefix.length()));
            s.append(", ");
        }
        s.delete(s.length() - 2, s.length());
        for (String tech : tag.getTechList()) {
            if (tech.equals(MifareClassic.class.getName())) {
                s.append('\n');
                MifareClassic mifareTag = MifareClassic.get(tag);
                String type = "Unknown";
                switch (mifareTag.getType()) {
                    case MifareClassic.TYPE_CLASSIC:
                        type = "Classic";
                        break;
                    case MifareClassic.TYPE_PLUS:
                        type = "Plus";
                        break;
                    case MifareClassic.TYPE_PRO:
                        type = "Pro";
                        break;
                }
                s.append("Mifare Classic type: ").append(type).append('\n');
                s.append("Mifare size: ").append(mifareTag.getSize() + " bytes").append('\n');
                s.append("Mifare sectors: ").append(mifareTag.getSectorCount()).append('\n');
                s.append("Mifare blocks: ").append(mifareTag.getBlockCount());
            }

            if (tech.equals(MifareUltralight.class.getName())) {
                s.append('\n');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()) {
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        type = "Ultralight";
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:
                        type = "Ultralight C";
                        break;
                }
                s.append("Mifare Ultralight type: ").append(type);
            }
        }

        return s.toString().getBytes();
    } //Only reading

    public NdefMessage[] RawNDEFContent(Intent intent) {
        byte[] empty = new byte[0];
        byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] payload = rawTagData(tag);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
        NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
        return new NdefMessage[]{msg};
    } //Only reading

    public void printTag(NdefMessage[] msgs) {
        for (NdefMessage msg : msgs) {
            for (NdefRecord rec : msg.getRecords()) {
                byte[] payload = rec.getPayload();
                String content = new String(payload);
                this.wai.printDebugInfo("Message: " + msg.toString());
                this.wai.printDebugInfo("Record: " + rec.toString());
                this.wai.printDebugInfo("Content: " + content);
                System.out.println("Content= " + content);
                System.out.println("");
            }
        }
        //wai.printDebugInfo(msgs.toString());
        //System.out.println(msgs);
    }

    private int writeTag(Tag tag, NdefMessage message) {
        this.wai.printDebugInfo("We inside Magic <3");
        try {
            int size = message.toByteArray().length;
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable()) {
                    wai.printDebugWarn("Tag is read-only.");
                    disableWrite();
                    return OnTagWriteListener.WRITE_ERROR_READ_ONLY;
                }
                if (ndef.getMaxSize() < size) {
                    wai.printDebugWarn("Tag capacity is " + ndef.getMaxSize() + " bytes, message is " +
                            size + " bytes.");
                    disableWrite();
                    return OnTagWriteListener.WRITE_ERROR_CAPACITY;
                }

                ndef.writeNdefMessage(message);
                disableWrite();
                return OnTagWriteListener.WRITE_OK;
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        disableWrite();
                        return OnTagWriteListener.WRITE_OK;
                    } catch (IOException e) {
                        disableWrite();
                        return OnTagWriteListener.WRITE_ERROR_IO_EXCEPTION;
                    }
                } else {
                    disableWrite();
                    return OnTagWriteListener.WRITE_ERROR_BAD_FORMAT;
                }
            }
        } catch (Exception e) {
            disableWrite();
            wai.printDebugError("Failed to write tag: " + e);
        }
        disableWrite();
        return OnTagWriteListener.WRITE_ERROR_IO_EXCEPTION;
    }

    public void enableWrite() {
        //allow write for next NFC intent
        if (enabled) {
            if (this.mWriteNdef != null) {
                this.WriteMode = true;
                Toast.makeText(caller, "Writemode enabled", Toast.LENGTH_LONG).show();
                wai.printDebugInfo("Please scan a NFC Tag to write on");
            } else {
                wai.printDebugInfo("No Data specified!");
            }
        }
    }

    public void disableWrite() {
        if (enabled) {
            this.wTAG = null;
            this.mWriteNdef = null;
            this.WriteMode = false;
            Toast.makeText(caller, "Writemode disabled", Toast.LENGTH_LONG).show();
        }
    }

    public void operate(NdefMessage[] msg) {
        for (NdefMessage ms : msg) {
            for (NdefRecord rec : ms.getRecords()) {
                byte[] type = rec.getType();
                String contenttype = new String(type);

                // if statements in case no switch-case possible with Strings; usually impleneted in JDK7

                if (contenttype.equals(Operations.OPC_CONTACT)) {
                    //automatically handled in android os
                }else if (contenttype.equals(Operations.OPC_SILENT)) {
                    Utils.toggleSilent(caller);
                    Toast.makeText(caller, "Mute Tag detected! Toggle Audiostate!", Toast.LENGTH_LONG).show();
                }
                /*switch (type){
                    case Operations.OPC_CONTACT:
                        break;
                    case Operations.OPC_SILENT:
                        break;
                    default:
                        wai.printDebugInfo("Could not resolve Tag-Operation");
                        break;

                }*/
            }
        }
    }

    public void createWriteNdef(NdefMessage message) {
        NdefMessage[] temp = {message};
        this.mWriteNdef = temp;
    }

    public void createWriteNdef(NdefMessage[] messages) {
        this.mWriteNdef = messages;
    }


}
