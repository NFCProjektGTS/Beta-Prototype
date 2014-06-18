package de.Beta.nfc_beta;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by Kern on 14.06.2014.
 */



public  class wContactFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_SECTION_NUMBER = "section_number";
    static InterfaceUI iface;
    TextView TextViewContactName;
    TextView TextViewContactPhone;
    String contactPayload;
    WritingAnimationFragment animateFragment;

    public wContactFragment() {
    }

    public static wContactFragment newInstance(int sectionNumber) {
        wContactFragment fragment = new wContactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
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
        animateFragment = new WritingAnimationFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.animation_frame, animateFragment)
                .commit();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        iface = MainActivity.iface;
    }
    void setContactName(String name){
        TextViewContactName.setText("Name: "+name);
    }
    void setContactPhone(String phone){
        TextViewContactPhone.setText("Nummer: "+phone);
    }

    @Override
    public void onClick(View view) {
        //iface = new InterfaceUI(getActivity());
        //TEST
        switch (view.getId()) {
            case  R.id.button_wchoosecontact: {
                iface.chooseContact();
                break;
            }
            case R.id.button_wcontact:{
                if(contactPayload!=null){
                    animateFragment.startAnimation();
                    iface.writeKontakt(contactPayload);
                    //if(iface.)
                    //animateFragment.stopAnimation();
                }
                else{ iface.showToast("Kein Kontakt gewählt!");}
                break;
            }
        }
    }
    void setContactPayload(String payload,String name,String phone){
        this.contactPayload= payload;
        setContactName(name);
        setContactPhone(phone);
    }
}


