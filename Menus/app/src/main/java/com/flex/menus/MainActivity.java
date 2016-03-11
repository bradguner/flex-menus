package com.flex.menus;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    Button moveButton;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.txtView);

        moveButton = (Button) findViewById(R.id.mvBtn);
        moveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                moveTheImage();
            }
        });
    }

    private void moveTheImage() {
        txtView.setText("It Worked!");
    }
}
