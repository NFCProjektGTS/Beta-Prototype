package de.Beta.nfc_beta;

/**
 * Created by Kern on 14.06.2014.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class wURLFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    static InterfaceUI iface;
    EditText wurlEditText;

    public wURLFragment() {
    }

    public static wURLFragment newInstance(int sectionNumber) {
        wURLFragment fragment = new wURLFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wurl, container, false);
        Button wurlButton = (Button) rootView.findViewById(R.id.button_wURL);
        wurlEditText = (EditText) rootView.findViewById(R.id.editText_wURL);
        wurlButton.setOnClickListener(this);

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
        switch (view.getId()) {
            case R.id.button_wURL: {
                //MainActivity.popup.show();
                iface.writeURL(wurlEditText.getText().toString());
                //new Dialog(getActivity(),1);
                WritingAnimationFragment animateFragment = new WritingAnimationFragment();
                //animateFragment.loadNfcAnimation();   TODO
                //animateFragment.startAnimation();     TODO

                AlertDialog dialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK)
                        .setTitle("NFC Tag beschreiben")
                        .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_wanimation, null))
                        .setCancelable(false)
                        .show();

                MainActivity.fragmentManager.beginTransaction()
                        .replace(R.id.animation_frame, animateFragment)
                        .commit();
                //animateFragment.startAnimation();

                break;
            }
        }
    }
}


