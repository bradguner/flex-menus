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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity  {

    // flags
    private static boolean menuVisible;
    private static boolean filterMenuVisible;
    private static int currentBg;
    private static int mode;
    private static int brightness;
    private static int blur;

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

        //sets flags default values
        menuVisible = false;
        filterMenuVisible = false;
        currentBg = 0;
        mode = 0; // 0 = none, 1 = brightness, 2 = blur
        brightness = 0;
        blur = 0;


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

    // NEEDED METHODS
    public void changeBgNormal() { RL.setBackgroundResource(R.drawable.bg_normal); currentBg = 0; }
    public void changeBgBlackWhite(View v) { RL.setBackgroundResource(R.drawable.bg_blackwhite); currentBg = 1; }
    public void changeBgSepia(View v) { RL.setBackgroundResource(R.drawable.bg_sepia); currentBg = 2; }
    public void changeBgHarsh(View v) { RL.setBackgroundResource(R.drawable.bg_harsh); currentBg = 3; }
    public void changeBgVintage(View v) { RL.setBackgroundResource(R.drawable.bg_vintage); currentBg = 4; }
    public void changeBgBlur1() { RL.setBackgroundResource(R.drawable.bg_blur1); currentBg = 5; }
    public void changeBgBlur2() { RL.setBackgroundResource(R.drawable.bg_blur2); currentBg = 6; }
    public void changeBgBright1() { RL.setBackgroundResource(R.drawable.bg_bright1); currentBg = 7; }
    public void changeBgBright2() { RL.setBackgroundResource(R.drawable.bg_bright2); currentBg = 8; }
    public void changeBgBright3() { RL.setBackgroundResource(R.drawable.bg_bright3); currentBg = 9; }
    public void changeBgBright4() { RL.setBackgroundResource(R.drawable.bg_bright4); currentBg = 10; }
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

    public void changeToBrightness(View v) {
        // change images
        v.setBackgroundResource(R.drawable.brightness_selected);
        //set flag
        mode = 1;
    }

    public void changeToBlur(View v) {
        // change images
        v.setBackgroundResource(R.drawable.blur_selected);
        // set flag
        mode = 2;
    }

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
    public void dogEarIn() {
        if (mode == 1) { // brightness
            switch (brightness) {
                case 0: // normal -> bright3
                    changeBgBright3();
                    brightness = 3;
                    break;
                case 1: //bright1 -> bright2
                    changeBgBright2();
                    brightness = 2;
                    break;
                case 2: // bright2 -> normal
                    changeBgNormal();
                    brightness = 0;
                    break;
                case 3: // bright3 -> bright4
                    changeBgBright4();
                    brightness = 4;
                    break;
                case 4: // bright4 -> bright1
                    changeBgBright1();
                    brightness = 1;
                    break;
            }
        }
        else if (mode == 2) { //blur

        }
    }

    // call this upon a dog ear bend out
    public void dogEarOut() {
        if (mode == 1) { // brightness
            switch (brightness) {
                case 0: // normal -> bright2
                    changeBgBright2();
                    brightness = 2;
                    break;
                case 1: //bright1 -> bright4
                    changeBgBright4();
                    brightness = 4;
                    break;
                case 2: // bright2 -> bright1
                    changeBgBright1();
                    brightness = 1;
                    break;
                case 3: // bright3 -> normal
                    changeBgNormal();
                    brightness = 0;
                    break;
                case 4: // bright4 -> bright3
                    changeBgBright3();
                    brightness = 3;
                    break;
            }
        }
        else if (mode == 2) { //blur

        }
    }

}
