package de.Beta.nfc_beta;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.Beta.nfc_beta.R;


/**
 * Created by Kern on 14.06.2014.
 */



public  class wContactFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_SECTION_NUMBER = "section_number";
    TextView TextViewContactName;
    TextView TextViewContactPhone;
    static InterfaceUI iface;

    public static wContactFragment newInstance(int sectionNumber) {
        wContactFragment fragment = new wContactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public wContactFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wcontact, container, false);
        Button chooseContactButton =(Button) rootView.findViewById(R.id.button_wchoosecontact);
        Button wContactButton =(Button) rootView.findViewById(R.id.button_wcontact);
        TextViewContactName   = (TextView)rootView.findViewById(R.id.textView_ContactName);
        TextViewContactPhone   = (TextView)rootView.findViewById(R.id.textView_ContactPhone);
        chooseContactButton.setOnClickListener(this);
        wContactButton.setOnClickListener(this);
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
            case  R.id.button_wchoosecontact: {
                iface.chooseContact();
                break;
            }
            case R.id.button_wcontact:{
                iface.writeKontakt();
                break;
            }
        }
    }
}


