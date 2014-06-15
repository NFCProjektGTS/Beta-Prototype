package de.Beta.nfc_beta;

/**
 * Created by Kern on 14.06.2014.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by Kern on 02.06.2014.
 */


/**
 * A placeholder fragment containing a simple view.
 */
public  class wSoundFragment extends Fragment implements View.OnClickListener{
    Sound msound = new Sound();
    private static final String ARG_SECTION_NUMBER = "section_number";
    static InterfaceUI iface;

    public wSoundFragment() {
    }

    public static wSoundFragment newInstance(int sectionNumber) {
        wSoundFragment fragment = new wSoundFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wsound, container, false);
        Button chooseSoundButton =(Button) rootView.findViewById(R.id.button_chooseSound);
        Button wSoundButton =(Button) rootView.findViewById(R.id.button_wSound);
        chooseSoundButton.setOnClickListener(this);
        wSoundButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        iface = MainActivity.iface;
    }
    @Override
    public void onClick(View view) {
        //iface = new InterfaceUI(getActivity());
        switch (view.getId()) {
            case  R.id.button_chooseSound: {
                iface.chooseSound();
                break;
            }
            case  R.id.button_wSound: {
                iface.writeSound();
                break;
            }
        }
    }
    //wenn die Sound Seite verlassen wird hört der Sound auf zu spielen
    @Override
    public void onPause() {
        //boolean offen = Fragment.isDetached();
        //if(offen == true) {
        //    msound.soundPausieren();
        //}
    }

}


