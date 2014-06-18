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
public class PictureFragment  extends Fragment {

    private static final String PICTURE = "section_number";

    public static PictureFragment newInstance(String picture) {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        args.putString(PICTURE, picture);
        fragment.setArguments(args);
        return fragment;
    }

    public PictureFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showpicture, container, false);
        //TODO HIER DAS BILD IN DIE BILDANZEIGE LADEN
        //BSP: so würde es mit text gehen, aber hier kommt die Bildanzeige rein
        //tagcontentView   = (TextView)rootView.findViewById(R.id.textView_TextTagContent);
        //tagcontentView.setText("Auf dem Tag befindet sich der Text: "+getArguments().getString(TEXT));
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);((MainActivity)activity).onSectionAttached(101);//SWITCH CASE FÜR PICTURE
    }

}

