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
import android.widget.EditText;

import de.Beta.nfc_beta.R;




public  class wTextFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    static InterfaceUI iface;
    EditText wtextEditText;

    public static wTextFragment newInstance(int sectionNumber) {
        wTextFragment fragment = new wTextFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public wTextFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wtext, container, false);
        Button wtextButton =(Button) rootView.findViewById(R.id.button_wtext);
        wtextEditText   = (EditText)rootView.findViewById(R.id.editText_wtext);
        wtextButton.setOnClickListener(this);
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
            case  R.id.button_wtext: {
                iface = new InterfaceUI(getActivity());
                iface.writeText(wtextEditText.getText().toString());
                break;
            }
        }
    }
}

