package de.Beta.nfc_beta;

/**
 * Created by Kern on 15.06.2014.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Kern on 03.06.2014.
 */



public  class TextFragment extends Fragment {

    private static final String TEXT = "section_number";
    TextView tagcontentView;
    public static TextFragment newInstance(String text) {
        TextFragment fragment = new TextFragment();
        Bundle args = new Bundle();
        args.putString(TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    public TextFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showtext, container, false);
        tagcontentView   = (TextView)rootView.findViewById(R.id.textView_TextTagContent);
        tagcontentView.setText("Auf dem Tag befindet sich der Text: "+getArguments().getString(TEXT));
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);((MainActivity)activity).onSectionAttached(102);//SWITCH CASE FÜR TEXT
    }
}
