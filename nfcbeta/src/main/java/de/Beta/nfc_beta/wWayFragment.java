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

import de.Beta.nfc_beta.R;


/**
 * Created by Kern on 02.06.2014.
 */


public  class wWayFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static wWayFragment newInstance(int sectionNumber) {
        wWayFragment fragment = new wWayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public wWayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wway, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}


