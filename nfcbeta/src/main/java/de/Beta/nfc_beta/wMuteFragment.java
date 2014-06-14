package de.Beta.nfc_beta;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.Beta.nfc_beta.R;


/**
 * Created by Kern on 02.06.2014.
 */


/**
 * A placeholder fragment containing a simple view.
 */
public  class wMuteFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    static InterfaceUI iface;

    public static wMuteFragment newInstance(int sectionNumber) {
        wMuteFragment fragment = new wMuteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public wMuteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wmute, container, false);

        Button wmuteButton =(Button) rootView.findViewById(R.id.button_wmute);

        wmuteButton.setOnClickListener(this);
        return rootView;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.button_wmute: {
                iface = new InterfaceUI(getActivity());
                iface.writeStummschalten();
                break;
            }
        }
    }
}


