package de.Beta.nfc_beta;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Kern on 16.06.2014.
 */
public class PictureFragment extends Fragment {

    private static final String PICTURE = "section_number";
    private static PictureFragment pFragment;
    private ImageView TagPictureViewer;


    public PictureFragment() {
    }

    public static PictureFragment newInstance(String picture) {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        args.putString(PICTURE, picture);
        fragment.setArguments(args);
        pFragment = fragment;
        return fragment;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showpicture, container, false);
        TagPictureViewer = (ImageView) rootView.findViewById(R.id.imageView_TagPicture);
        InputStream ims = null;
        System.out.println(getArguments().getString(PICTURE));
        try {
            ims = getActivity().getAssets().open("pictures/" + getArguments().getString(PICTURE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(ims, null);
        TagPictureViewer.setImageDrawable(d);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(101);//SWITCH CASE FÜR PICTURE
    }

}

