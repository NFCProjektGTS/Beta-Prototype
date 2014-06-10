package de.Beta.nfc_beta;

/**
 * Created by Kern on 03.06.2014.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.Beta.nfc_beta.R;


/**
 * Created by Kern on 02.06.2014.
 */


public class DebugFragment extends Fragment implements DebugChangeListener.Listener{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static List<String> mDataSourceList = new ArrayList<String>();
    private static ListView listView;
    private static ArrayAdapter a;
    private static DebugChangeListener dcl;

    public static DebugFragment newInstance(int sectionNumber) {
        DebugFragment fragment = new DebugFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public DebugFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_debug, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dcl = new DebugChangeListener();
        dcl.registerListener(this);

        listView = (ListView) getActivity().findViewById(R.id.debuglist);
        a= new ArrayAdapter(getActivity(), R.layout.debuglist, mDataSourceList){
            @Override
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextSize(5,1.9f);
                if(textView.getText().charAt(0)=='I'){
                    textView.setTextColor(new Color().parseColor("#D6D6D6"));
                }else if(textView.getText().charAt(0)=='W'){
                    textView.setTextColor(new Color().parseColor("#FFF62E"));
                }else if(textView.getText().charAt(0)=='E'){
                    textView.setTextColor(new Color().parseColor("#FF3E2E"));
                }
                return view;
            }
        };
        listView.setAdapter(a);



    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
    public  void addLine(int type, String line){
        switch (type){
            case 0:
                mDataSourceList.add("I: "+line);
                break;
            case 1:
                mDataSourceList.add("W: "+line);
                break;
            case 2:
                mDataSourceList.add("E: "+line);
                break;
        }
        if(a!=null){
            dcl.changed();
        }else {
            System.out.println(line);
        }

    }


    @Override
    public void onStateChange() {
        a.notifyDataSetChanged();

        listView.post(new Runnable() {
           @Override
           public void run() {
                listView.setSelection(a.getCount() - 1);
           }
        });

    }
}
