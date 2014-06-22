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
import java.util.Iterator;


/**
 * Created by Kern on 02.06.2014.
 */


/**
 * A placeholder fragment containing a simple view.
 */
public class wSoundFragment extends Fragment implements View.OnClickListener, ListView.OnItemClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static InterfaceUI iface;
    private ArrayList<String> soundList;
    private ListView listViewSound;
    private ArrayList<String> fileList;
    private String selectedSound;
    private Button wSoundButton;

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
        wSoundButton = (Button) rootView.findViewById(R.id.button_wSound);
        wSoundButton.setOnClickListener(this);
        wSoundButton.setEnabled(false);
        listViewSound = (ListView) rootView.findViewById(R.id.listView_sound);
        listViewSound.setOnItemClickListener(this);

        try {
            //TODO falsche sounds dabei! FATAL!!
            String[] soundNames = getActivity().getApplicationContext().getAssets().list("sounds");
            soundList = new ArrayList<String>();
            Collections.addAll(soundList, soundNames);
            Iterator<String> stringIterator = soundList.iterator();
            while (stringIterator.hasNext()) {
                String string = stringIterator.next();
                if (!string.matches("([^\\s]+(\\.(?i)(mp3|wav|ogg|wma))$)")) {
                    stringIterator.remove();
                }
            }
            fileList = new ArrayList<String>(soundList);
            for (String s : soundList) {
                if (s.matches("([^\\s]+(\\.(?i)(mp3|wav|ogg|wma))$)")) {
                    int i = soundList.indexOf(s);
                    soundList.set(i, s.replace(".wav", ""));
                    soundList.set(i, s.replace(".ogg", ""));
                    soundList.set(i, s.replace(".wma", ""));
                    soundList.set(i, s.replace(".mp3", ""));
                }
            }
            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.assetlist, soundList);
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
            case R.id.button_wSound: {
                if (selectedSound != null) {
                    iface.writeSound(selectedSound);
                    //animation
                } else {
                    iface.showToast("Kein Sound gewählt!");
                }
                break;
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        wSoundButton.setEnabled(true);
        selectedSound = fileList.get(i);
        iface.showToast("Sound ausgewhählt: " + selectedSound);
        //sound.soundAbspielen(soundList.get(i));
        //sound.soundAbspielen("door.mp3");
    }
}


