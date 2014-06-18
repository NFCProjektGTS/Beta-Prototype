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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Kern on 02.06.2014.
 */


/**
 * A placeholder fragment containing a simple view.
 */
public class wSoundFragment extends Fragment implements View.OnClickListener, ListView.OnItemClickListener {
    private ArrayList<String> soundList;
    private ListView listViewSound;
    private String selectedSound;
    private static final String ARG_SECTION_NUMBER = "section_number";
    static InterfaceUI iface;
    Sound sound =MainActivity.sound;

    public wSoundFragment() {
    }

    public static wSoundFragment newInstance(int sectionNumber) {
        wSoundFragment fragment = new wSoundFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wsound, container, false);
        Button chooseSoundButton =(Button) rootView.findViewById(R.id.button_chooseSound);
        Button wSoundButton =(Button) rootView.findViewById(R.id.button_wSound);
        chooseSoundButton.setOnClickListener(this);
        wSoundButton.setOnClickListener(this);
        listViewSound = (ListView) rootView.findViewById(R.id.listView_sound);
        listViewSound.setOnItemClickListener(this);

        try {
            String[] soundNames = getActivity().getApplicationContext().getAssets().list("sounds");
            soundList = new ArrayList<String>();
            Collections.addAll(soundList, soundNames);
            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.debuglist,soundList);
            listViewSound.setAdapter(listAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }



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
            case  R.id.button_chooseSound: {
                iface.chooseSound();
                break;
            }
            case  R.id.button_wSound: {
                System.out.println(selectedSound);
                iface.writeSound(selectedSound);
                break;
            }
        }
    }


    public void setSoundList(String[] soundNames){


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //selectedSound = soundList.get(i);

        sound.soundAbspielen(soundList.get(i));
        //sound.soundAbspielen("door.mp3");
    }
}


