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

import de.Beta.nfc_beta.R;


public  class wPictureFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    static InterfaceUI iface;

    public static wPictureFragment newInstance(int sectionNumber) {
        wPictureFragment fragment = new wPictureFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public wPictureFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wpicture, container, false);
        Button choosePictureButton =(Button) rootView.findViewById(R.id.button_choosePicture);
        Button wPictureButton =(Button) rootView.findViewById(R.id.button_wPicture);
        choosePictureButton.setOnClickListener(this);
        wPictureButton.setOnClickListener(this);
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
        iface = new InterfaceUI(getActivity());
        switch (view.getId()) {
            case  R.id.button_choosePicture: {
                iface.choosePicture();
                break;
            }
            case  R.id.button_wPicture: {
                iface.writePicture();
                break;
            }
        }
    }
}


