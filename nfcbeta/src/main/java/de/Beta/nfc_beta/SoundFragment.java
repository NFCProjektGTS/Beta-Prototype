
package de.Beta.nfc_beta;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kern on 16.06.2014.
 */
public class SoundFragment  extends Fragment {

    private static final String SOUND = "section_number";

    public static SoundFragment  newInstance(String sound) {
        SoundFragment  fragment = new SoundFragment ();
        Bundle args = new Bundle();
        args.putString(SOUND, sound);
        fragment.setArguments(args);
        return fragment;
    }

    public SoundFragment () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showsound, container, false);
        //TODO HIER DAS SOUND ABSPIELEN REIN
        //BSP: so würde es mit text gehen, aber hier kommt das Sound abspielen rein
        //tagcontentView   = (TextView)rootView.findViewById(R.id.textView_TextTagContent);
        //tagcontentView.setText("Auf dem Tag befindet sich der Text: "+getArguments().getString(TEXT));
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}



