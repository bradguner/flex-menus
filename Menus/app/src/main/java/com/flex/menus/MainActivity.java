package com.flex.menus;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements AnimationListener {

    private Button moveButton;
    private TextView txtView;
    private ImageView imgView;
    private static boolean menuVisible;
    private LinearLayout LL;
    Animation animShow;
    Animation animHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.txtView);
        imgView = (ImageView) findViewById(R.id.imageView);
        LL = (LinearLayout) this.findViewById(R.id.MenuLayout);

        // Animations
        animShow = AnimationUtils.loadAnimation(this, R.anim.slide_in_menu);
        animShow.setAnimationListener(this);
        animHide = AnimationUtils.loadAnimation(this, R.anim.slide_out_menu);
        animHide.setAnimationListener(this);

        // hide menu upon creation
        menuVisible = false;
        imgView.startAnimation(animHide);

        // button to mimic bend gesture
        moveButton = (Button) findViewById(R.id.mvBtn);
        moveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (menuVisible) {
                    hideMenu(animHide);
                }
                else {
                    displayMenu(animShow);
                }
            }
        });

        // run the bluetooth reading part

    }

    // react to a bend and based on what integer was sent react accordingly
    // bend values

    /**
     * bend
     * 1 : Side bend inward
     * 2 : Side bend outward
     * 3 : Dog ear in
     * 4 : Dog ear out
     */
    public void reactToBend(int bend) {
        switch(bend) {
            case 1:
                if (!menuVisible) {
                    displayMenu(animShow);
                }
                // future
                // if !menuVisible and certain touch event then perform action
                // if !menuVisible and no touch, show menu
                break;
            case 2:
                if (menuVisible) {
                    hideMenu(animHide);
                }
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
    }

    // starts animation to display menu
    private void displayMenu(Animation anim) {
        txtView.setText("Showing!");
        imgView.startAnimation(anim);
        menuVisible = true;
    }

    // starts animation to hide menu
    private void hideMenu(Animation anim) {
        txtView.setText("Hidden!");
        imgView.startAnimation(anim);
        menuVisible = false;
    }

    @Override
    public void onAnimationEnd(Animation animation){}

    @Override
    public void onAnimationRepeat(Animation animation){}

    @Override
    public void onAnimationStart(Animation animation) {
        LL.layout(0, -(int)this.getResources().getDimension(R.dimen.menu_offset), LL.getWidth(), LL.getHeight() + (int)this.getResources().getDimension(R.dimen.menu_offset));
    }
}
