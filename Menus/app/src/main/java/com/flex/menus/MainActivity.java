package com.flex.menus;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity  {

    private Button moveButton;
    private static boolean menuVisible;
    public RelativeLayout RL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RL = (RelativeLayout) findViewById(R.id.rela);

        menuVisible = false;

        // button to mimic bend gesture
        moveButton = (Button) findViewById(R.id.mvBtn);
        moveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (menuVisible) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);

                    Fragment f = getFragmentManager().findFragmentByTag("generalMenu");
                    fragmentTransaction.remove(f);
                    fragmentTransaction.commit();

                    menuVisible = false;
                }
                else {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);

                    MenuFragment fragment = new MenuFragment();
                    fragmentTransaction.add(R.id.fragmentContainer, fragment,"generalMenu");
                    fragmentTransaction.commit();

                    menuVisible = true;
                }
            }
        });

    } // end onCreate


    // Change Background Methods
    public void changeBackgroundStar(View v){
        RL.setBackgroundResource(R.drawable.star_photo);
    }
    public void changeBackgroundSpace(View v){
        RL.setBackgroundResource(R.drawable.space_photo);
    }
    public void changeBackgroundTemperature(View v){
        RL.setBackgroundResource(R.drawable.temperature_photo);
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
    /*
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

    */

}
