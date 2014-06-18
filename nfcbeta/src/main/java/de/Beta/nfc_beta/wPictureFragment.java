package de.Beta.nfc_beta;

/**
 * Created by Kern on 14.06.2014.
 */

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;


public  class wPictureFragment extends Fragment implements View.OnClickListener, ListView.OnItemClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    static InterfaceUI iface;
    private ArrayList<String> imageList;
    private ListView listViewPictures;
    private String selectedPicture;
    private ImageView selectedPictureViewer;


    public wPictureFragment() {
    }

    public static wPictureFragment newInstance(int sectionNumber) {
        wPictureFragment fragment = new wPictureFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wpicture, container, false);


        Button choosePictureButton =(Button) rootView.findViewById(R.id.button_choosePicture);
        choosePictureButton.setOnClickListener(this);

        Button wPictureButton =(Button) rootView.findViewById(R.id.button_wPicture);
        wPictureButton.setOnClickListener(this);

        listViewPictures = (ListView) rootView.findViewById(R.id.listView_pictures);
        listViewPictures.setOnItemClickListener(this);

        selectedPictureViewer = (ImageView) rootView.findViewById(R.id.imageView_selectedPicture);

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
        //iface = new InterfaceUI(getActivity());
        switch (view.getId()) {
            case  R.id.button_choosePicture: {
                iface.choosePicture();
                break;
            }
            case  R.id.button_wPicture: {
                if(selectedPicture!=null){
                    iface.writePicture(selectedPicture);
                }
                MainActivity.showPictureFragment("clecle.jpg");
                break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.listView_pictures:{
                selectedPicture = imageList.get(position);
                showSelectedPicture();

            }
        }
    }

    private void showSelectedPicture(){
        if(selectedPicture!=null){
            try{
                InputStream iS = getActivity().getAssets().open("pictures/"+selectedPicture);
                Drawable d = Drawable.createFromStream(iS,null);
                selectedPictureViewer.setImageDrawable(d);
            }
            catch(Exception e){

            }

        }
    }

    public void setImageList(String[] imageNames){
        this.imageList = new ArrayList<String>();
        Collections.addAll(imageList, imageNames);


        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.debuglist,this.imageList);
        listViewPictures.setAdapter(listAdapter);

    }


}


