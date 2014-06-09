package de.Beta.nfc_beta;

import android.app.Activity;
import android.content.Context;

import de.Beta.nfc_beta.DebugFragment;

/**
 * Created by Kern on 03.06.2014.
 */
public class InterfaceUI {
    Context mContext;

    InterfaceUI(Context c) {
        mContext = c;
    }


    void showToast() {

    }

    public void printDebugInfo(String text) {
        DebugFragment.addLine(0, text);
    }

    public void printDebugWarn(String text) {
        DebugFragment.addLine(1, text);
    }

    public void printDebugError(String text) {
        DebugFragment.addLine(2, text);
    }
}

