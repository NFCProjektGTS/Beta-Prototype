package de.Beta.nfc_beta;

/**
 * Created by Kern on 10.06.2014.
 */
public class DebugChangeListener {

    public void changed() {
        if (mListener != null)
            mListener.onStateChange();
    }

    public interface Listener {
        public void onStateChange();
    }

    private Listener mListener = null;

    public void registerListener (Listener listener) {
        mListener = listener;
    }





}
