package com.flex.menus;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
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

    Button moveButton;
    TextView txtView;
    ImageView imgView;
    private boolean menuVisible;
    private LinearLayout LL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.txtView);
        imgView = (ImageView) findViewById(R.id.imageView);

        LL = (LinearLayout) this.findViewById(R.id.MenuLayout);




        //finally
        final Animation animShow = AnimationUtils.loadAnimation(this, R.anim.slide_in_menu);
        animShow.setAnimationListener(this);

        final Animation animHide = AnimationUtils.loadAnimation(this, R.anim.slide_out_menu);
        animHide.setAnimationListener(this);

        menuVisible = false;
        imgView.startAnimation(animHide);


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


    }



    private void displayMenu(Animation anim) {
        txtView.setText("Showing!");
        imgView.startAnimation(anim);
        menuVisible = true;
    }

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
    public void onAnimationStart(Animation animation)
    {
        // This is the key...
        //set the coordinates for the bounds (left, top, right, bottom) based on the offset value (50px) in a resource XML
        LL.layout(0, -(int)this.getResources().getDimension(R.dimen.menu_offset),
                LL.getWidth(), LL.getHeight() + (int)this.getResources().getDimension(R.dimen.menu_offset));
    }
}
