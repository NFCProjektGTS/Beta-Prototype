
package de.Beta.nfc_beta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kern on 16.06.2014.
 */
public class SoundFragment  extends Fragment {

    public static final String MESSAGE = "de.Beta.nfc_beta.FILEMESSAGE";
    private static final String SOUND = "section_number";

    public SoundFragment() {
    }

    public static SoundFragment newInstance(String sound) {
        SoundFragment fragment = new SoundFragment();
        Bundle args = new Bundle();
        args.putString(SOUND, sound);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showsound, container, false);
        Intent intent = new Intent(getActivity(), MediaPlaybackActivity.class);
        intent.putExtra(MESSAGE, savedInstanceState.getString(SOUND));
        startActivity(intent);

        //TODO HIER DAS SOUND ABSPIELEN REIN
        //BSP: so würde es mit text gehen, aber hier kommt das Sound abspielen rein
        //tagcontentView   = (TextView)rootView.findViewById(R.id.textView_TextTagContent);
        //tagcontentView.setText("Auf dem Tag befindet sich der Text: "+getArguments().getString(TEXT));
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);((MainActivity)activity).onSectionAttached(103);//SWITCH CASE FÜR SOUND
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}



