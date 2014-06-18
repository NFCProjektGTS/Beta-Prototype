package de.Beta.nfc_beta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Marlon on 14.06.2014.
 */

public class WritingAnimationFragment extends Fragment {

    private Animation animation;
    private ArrayList<ImageView> images = new ArrayList<ImageView>();

    public WritingAnimationFragment(){
        animation = new TranslateAnimation(0, -230, 0, 0);
        animation.setDuration(1800);
        animation.setRepeatCount(5);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_writing_animation, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void loadNfcAnimation() {
        images.clear();
        images.add((ImageView) this.getView().findViewById(R.id.imgViewTag));
        images.add((ImageView) this.getView().findViewById(R.id.imgViewHandy));
    }


    public void startAnimation(){
        images.get(0).setVisibility(View.VISIBLE);
        images.get(1).setVisibility(View.VISIBLE);
        images.get(1).startAnimation(animation);

        //img.setImageResource(R.drawable.handy);
        //ObjectAnimator animation = ObjectAnimator.ofFloat(img,"x", 200);
        //img.setImageResource(R.drawable.bild_b);
    }

    public void stopAnimation(){
        animation.cancel();
    }


}
