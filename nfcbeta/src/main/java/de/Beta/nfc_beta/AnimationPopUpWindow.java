package de.Beta.nfc_beta;

import android.app.Activity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by Clemens on 19.06.2014.
 */
public class AnimationPopUpWindow extends PopupWindow {
    WritingAnimationFragment animationFragment;
    LinearLayout layout;

    AnimationPopUpWindow(Activity caller) {
        super(caller);
        layout = new LinearLayout(caller);
        setFocusable(false);
        setContentView(layout);

    }

    public void show() {
        showAtLocation(layout, Gravity.BOTTOM, 10, 10);
        update(50, 50, 300, 300);
    }
}
