package de.Beta.nfc_beta;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Noli on 20.06.2014.
 */
public class AlertDialogAnimation extends AlertDialog {
    Animation animation;
    private ArrayList<ImageView> images = new ArrayList<ImageView>();
    private View view;

    AlertDialogAnimation(Activity caller, int THEME) {
        super(caller, THEME);
        animation = new TranslateAnimation(0, -230, 0, 0);
        animation.setDuration(1800);
        animation.setRepeatCount(5);
        //view = caller.getLayoutInflater().inflate(R.layout.dialog_wanimation, null);

        LayoutInflater inflater = (LayoutInflater) caller.getSystemService(caller.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dialog_wanimation, null);
        setContentView(view);

        setTitle("NFC Tag beschreiben");

        setView(view);
        setCancelable(false);
        ImageView tag = (ImageView) view.findViewById(R.id.imgViewTag);
        tag.setVisibility(View.VISIBLE);

        ImageView handy = (ImageView) view.findViewById(R.id.imgViewHandy);
        handy.setVisibility(View.VISIBLE);
        show();
    }

    public void startAnimation() {
        images.get(0).setVisibility(View.VISIBLE);
        images.get(1).setVisibility(View.VISIBLE);
        images.get(1).startAnimation(animation);

        //img.setImageResource(R.drawable.handy);
        //ObjectAnimator animation = ObjectAnimator.ofFloat(img,"x", 200);
        //img.setImageResource(R.drawable.bild_b);
    }
}
