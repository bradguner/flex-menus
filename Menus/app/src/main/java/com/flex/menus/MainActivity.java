package com.flex.menus;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity  {

    // flags
    private static boolean menuVisible;
    private static boolean filterMenuVisible;
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

    public void changeBgNormal(View v) { RL.setBackgroundResource(R.drawable.bg_normal); }
    public void changeBgNormal2() { RL.setBackgroundResource(R.drawable.bg_normal); }
    public void changeBgBlackWhite(View v) { RL.setBackgroundResource(R.drawable.bg_blackwhite); }
    public void changeBgSepia(View v) { RL.setBackgroundResource(R.drawable.bg_sepia); }
    public void changeBgHarsh(View v) { RL.setBackgroundResource(R.drawable.bg_harsh); }
    public void changeBgVintage(View v) { RL.setBackgroundResource(R.drawable.bg_vintage); }
    public void changeBgBlur1() { RL.setBackgroundResource(R.drawable.bg_blur1); }
    public void changeBgBlur2() { RL.setBackgroundResource(R.drawable.bg_blur2); }
    public void changeBgBright1() { RL.setBackgroundResource(R.drawable.bg_bright1); }
    public void changeBgBright2() { RL.setBackgroundResource(R.drawable.bg_bright2); }
    public void changeBgBright3() { RL.setBackgroundResource(R.drawable.bg_bright3); }
    public void changeBgBright4() { RL.setBackgroundResource(R.drawable.bg_bright4); }


    // change to the filter menu
    public void changeToFilterMenu(View v){
        filterMenuVisible = true;
        menuVisible = false;
        mode = 0;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MenuFragment filterFragment = new MenuFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, filterFragment,"FilterMenuFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // change to selected brightness icon and set flag for mode
    public void changeToBrightness(View v) {
        // change images
        v.setBackgroundResource(R.drawable.brightness_selected);
        //set flag
        mode = 1;
    }

    // change to selected blur icon and set flag for mode
    public void changeToBlur(View v) {
        // change images
        v.setBackgroundResource(R.drawable.blur_selected);
        // set flag
        mode = 2;
    }

    // call this upon a side bend in
    public void bendIn() {
        if (menuVisible) {
            // do nothing
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
                    changeBgNormal2();
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
                default:
                    break;
            }
        }
        else if (mode == 2) { //blur
            switch (blur) {
                case 0: // normal -> blur1
                    changeBgBlur1();
                    blur = 1;
                    break;
                case 1: // blur1 -> blur2
                    changeBgBlur2();
                    blur = 2;
                    break;
                case 2: // blur2 -> normal
                    changeBgNormal2();
                    blur = 0;
                    break;
                default:
                    break;
            }
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
                    changeBgNormal2();
                    brightness = 0;
                    break;
                case 4: // bright4 -> bright3
                    changeBgBright3();
                    brightness = 3;
                    break;
                default:
                    break;
            }
        }
        else if (mode == 2) { //blur
            switch (blur) {
                case 0: // normal -> blur2
                    changeBgBlur2();
                    blur = 2;
                    break;
                case 1: // blur1 -> normal
                    changeBgNormal2();
                    blur = 0;
                    break;
                case 2: // blur2 -> blur1
                    changeBgBlur1();
                    blur = 1;
                    break;
                default:
                    break;
            }
        }
    }

}
