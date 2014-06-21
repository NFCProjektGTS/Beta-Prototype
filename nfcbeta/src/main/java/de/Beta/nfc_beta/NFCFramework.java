package de.Beta.nfc_beta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;

import java.io.IOException;
import java.math.BigInteger;

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

        if (enabled) wai.printDebugInfo("Adapter found: " + mNfcAdapter.toString());

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        this.mTagFilters = new IntentFilter[]{tagDetected};

    }

    public boolean isEnabled() {
        return enabled;
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
                wai.printDebugWarn("NFC is disabled");
                wai.showToast("Please enable NFC in the settings");
                new Dialog(caller, 0);
            }
            if (mNfcAdapter.isEnabled()) {
                this.enabled = true;
                return true;
            }
        } else {
            wai.showToast("NFC Hardware nicht gefunden!");
            wai.printDebugError("NFC Hardware nicht gefunden!");
        }
        this.enabled = false;
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
                    int code = writeTag(wTAG, mWriteNdef[0]);
                    wai.printDebugInfo(OnTagWriteListener.onTagWrite(code));
                    if (MainActivity.dialogAnimation != null) {
                        MainActivity.dialogAnimation.closeDialog();
                    }
                }
            }
        }

    }


    private byte[] rawTagData(Parcelable parc) {
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

    private NdefMessage[] RawNDEFContent(Intent intent) {
        byte[] empty = new byte[0];
        byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] payload = rawTagData(tag);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
        NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
        return new NdefMessage[]{msg};
    } //Only reading

    private void printTag(NdefMessage[] msgs) {
        for (NdefMessage msg : msgs) {
            for (NdefRecord rec : msg.getRecords()) {
                byte[] payload = rec.getPayload();
                String content = new String(payload);
                this.wai.printDebugInfo("Message: " + msg.toString());
                this.wai.printDebugInfo("Record: " + rec.toString());
                this.wai.printDebugInfo("Content: " + content);
            }
        }
        //wai.printDebugInfo(msgs.toString());
        //System.out.println(msgs);
    }

    public int writeTag(Tag tag, NdefMessage message) {
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
        } catch (TagLostException e) {
            disableWrite();
            wai.printDebugError("Failed to write. Tag out of range.");
            return OnTagWriteListener.WRITE_ERROR_TAG_LOST;
        } catch (IOException e) {
            disableWrite();
            wai.printDebugError("Failed to write. I/O Error");
            return OnTagWriteListener.WRITE_ERROR_IO_EXCEPTION;
        } catch (FormatException e) {
            disableWrite();
            wai.printDebugError("Failed to write. Tag unformatable!");
            return OnTagWriteListener.WRITE_ERROR_BAD_FORMAT;
        }

    }

    public void enableWrite() {
        //allow write for next NFC intent
        if (enabled) {
            if (this.mWriteNdef != null) {
                this.WriteMode = true;
                wai.printDebugInfo("Writemode enabled");
                wai.printDebugInfo("Please scan a NFC Tag to write on");

                MainActivity.dialogAnimation = new AlertDialogAnimation(caller, AlertDialog.THEME_HOLO_LIGHT);
                MainActivity.dialogAnimation.loadNFCAnimation();
                MainActivity.dialogAnimation.startAnimation();
            } else {
                wai.printDebugWarn("No Data specified!");
            }
        }
    }

    public void disableWrite() {
        if (enabled) {
            this.wTAG = null;
            this.mWriteNdef = null;
            this.WriteMode = false;
            this.payload = "";
            wai.showToast("Writemode disabled");
        }
    }

    public void operate(NdefMessage[] msg) {
        for (NdefMessage ms : msg) {
            for (NdefRecord rec : ms.getRecords()) {
                switch (new BigInteger(rec.getType()).intValue()) {
                    case 1001:
                        Operations.toggleSilent(caller);
                        wai.showToast("Mute Tag detected! Toggle Audiostate.");
                        break;
                    case 1002:
                        //Process load contact
                        break;
                    case 1003:
                        Operations.initSound(rec);
                        wai.showToast("Sound Tag detected! Play Sound.");
                        break;
                    case 1004:
                        Operations.initImage(rec);
                        wai.showToast("Image Tag detected! Display Image.");
                        break;
                    case 1005:
                        //GPS Data
                        break;
                    case 1006:
                        Operations.initText(rec);
                        wai.showToast("Text Tag detected! Display Text");
                        break;
                    default:
                        //
                        break;
                }
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