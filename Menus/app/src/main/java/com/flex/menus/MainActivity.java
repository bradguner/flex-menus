package com.flex.menus;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity  {

    // flags
    private static boolean menuVisible;
    private static boolean filterMenuVisible;

    public RelativeLayout RL;

    // bend buttons - remove after
    private Button bendInButton;
    private Button bendOutButton;
    private Button deInButton;
    private Button deOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RL = (RelativeLayout) findViewById(R.id.rela);

        menuVisible = false;
        filterMenuVisible = false;

        // button to mimic bend gesture - remove after
        bendInButton = (Button) findViewById(R.id.bendInBtn);
        bendInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bendIn();
            }
        });
        bendOutButton = (Button) findViewById(R.id.bendOutBtn);
        bendOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bendOut();
            }
        });
        deInButton = (Button) findViewById(R.id.deInBtn);
        deInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dogEarIn();
            }
        });
        deOutButton = (Button) findViewById(R.id.deOutBtn);
        deOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dogEarOut();
            }
        });


    } // end onCreate


    // Change Background Methods - Remove these after
    public void changeBackgroundStar(View v){
        RL.setBackgroundResource(R.drawable.star_photo);
    }
    public void changeBackgroundSpace(View v){
        RL.setBackgroundResource(R.drawable.space_photo);
    }
    public void changeBackgroundTemperature(View v){
        RL.setBackgroundResource(R.drawable.temperature_photo);
    }
    public void changeBackgroundImage(View v, int id) {
        /*
            id      bg
            0       bg_normal
            1       bg_blackwhite
            2       bg_sepia
            3       bg_harsh
            4       bg_vintage
            5       bg_blur1
            6       bg_blur2
            7       bg_bright1
            8       bg_bright2
            9       bg_bright3
            10      bg_bright4
        */
        switch(id) {
            case 0:
                RL.setBackgroundResource(R.drawable.bg_normal);
                break;
            case 1:
                RL.setBackgroundResource(R.drawable.bg_blackwhite);
                break;
            case 2:
                RL.setBackgroundResource(R.drawable.bg_sepia);
                break;
            case 3:
                RL.setBackgroundResource(R.drawable.bg_harsh);
                break;
            case 4:
                RL.setBackgroundResource(R.drawable.bg_vintage);
                break;
            case 5:
                RL.setBackgroundResource(R.drawable.bg_blur1);
                break;
            case 6:
                RL.setBackgroundResource(R.drawable.bg_blur2);
                break;
            case 7:
                RL.setBackgroundResource(R.drawable.bg_bright1);
                break;
            case 8:
                RL.setBackgroundResource(R.drawable.bg_bright2);
                break;
            case 9:
                RL.setBackgroundResource(R.drawable.bg_bright3);
                break;
            case 10:
                RL.setBackgroundResource(R.drawable.bg_bright4);
                break;
            default:
                break;
        }
    }

    public void changeToFilterMenu(View v){
        filterMenuVisible = true;
        menuVisible = false;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MenuFragment filterFragment = new MenuFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, filterFragment,"FilterMenuFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*
            if (menuVisible) { //close main menu frgament
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);

            Fragment f = getFragmentManager().findFragmentByTag("mainMenuFragment");
            fragmentTransaction.remove(f);
            fragmentTransaction.commit();

            menuVisible = false;
     */


    // call this upon a side bend in
    public void bendIn() {
        if (menuVisible) {

        } else if (!menuVisible && filterMenuVisible) { //filter menu is visible, return to main frgament
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStackImmediate();
            menuVisible = true;
            filterMenuVisible = false;

        } else { //no fragments visible, display main menu fragment
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);

            MainMenuFragment fragment = new MainMenuFragment();
            fragmentTransaction.add(R.id.fragmentContainer, fragment, "mainMenuFragment");
            fragmentTransaction.commit();

            menuVisible = true;
        }
    }

    // call this upon a side bend out
    public void bendOut() {
        if (menuVisible) { //close main menu frgament
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);

            Fragment f = getFragmentManager().findFragmentByTag("mainMenuFragment");
            fragmentTransaction.remove(f);
            fragmentTransaction.commit();

            menuVisible = false;
        } else if (!menuVisible && filterMenuVisible) { //filter menu is visible, return to main frgament
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStackImmediate();
            menuVisible = true;
            filterMenuVisible = false;
        }
    }

    // call this upon a dog ear bend in
    public void dogEarIn() {}

    // call this upon a dog ear bend out
    public void dogEarOut() {}

}
