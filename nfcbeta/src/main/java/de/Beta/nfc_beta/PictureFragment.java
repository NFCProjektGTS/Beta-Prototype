package de.Beta.nfc_beta;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Kern on 16.06.2014.
 */
public class PictureFragment  extends Fragment {

    private static final String PICTURE = "section_number";
    private ImageView TagPictureViewer;
    private static PictureFragment pFragment;


    public static PictureFragment newInstance(String picture) {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
       // args.putString(PICTURE, picture);
        args.putString(PICTURE, "potter");
        fragment.setArguments(args);
        pFragment = fragment;
        return fragment;


    }

    public PictureFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showpicture, container, false);
        TagPictureViewer = (ImageView) rootView.findViewById(R.id.imageView_TagPicture);

        //TODO HIER DAS BILD IN DIE BILDANZEIGE LADEN
        //BSP: so würde es mit text gehen, aber hier kommt die Bildanzeige rein
        //tagcontentView   = (TextView)rootView.findViewById(R.id.textView_TextTagContent);
        //tagcontentView.setText("Auf dem Tag befindet sich der Text: "+getArguments().getString(PICTURE));
        return rootView;
    }

    public void BildAnzeigen(){
        BildAnzeigen(pFragment);
        //ImageView imgResBilder = (ImageView) this.getView().findViewById(R.id.imageViewResBilder);
        //imgResBilder.

        //this.onCreateView(R.id.scrollViewBilder);
        //bilderList.

        if((PICTURE!=null)){

            try{
                InputStream iS = getActivity().getAssets().open("pictures/"+PICTURE);
                Drawable d = Drawable.createFromStream(iS,null);
                TagPictureViewer.setImageDrawable(d);
            }
            catch(Exception e){

            }

        }
    }

    public void BildAnzeigen(PictureFragment pF){

        //ImageView imgResBilder = (ImageView) this.getView().findViewById(R.id.imageViewResBilder);
        //imgResBilder.

        //this.onCreateView(R.id.scrollViewBilder);
        //bilderList.

        if((PICTURE!=null)){

            try{
                InputStream iS = getActivity().getAssets().open("pictures/"+PICTURE);
                Drawable d = Drawable.createFromStream(iS,null);
                TagPictureViewer.setImageDrawable(d);
            }
            catch(Exception e){

            }

        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);((MainActivity)activity).onSectionAttached(101);//SWITCH CASE FÜR PICTURE
    }

}

