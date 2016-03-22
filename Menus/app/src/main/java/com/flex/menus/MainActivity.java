package com.flex.menus;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends Activity  {

    // flags
    private static boolean menuVisible;
    private static boolean filterMenuVisible;
    private static int mode;
    private static int brightness;
    private static int blur;
    private View lastButtonPressed;

    public RelativeLayout RL;

    // bend buttons - remove after
    private Button bendInButton;
    private Button bendOutButton;
    private Button deInButton;
    private Button deOutButton;
    private BluetoothDevice mDevice;

    Thread mConnectThread;
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RL = (RelativeLayout) findViewById(R.id.rela);

        //get the bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Context context = getApplicationContext();
            CharSequence text = "Bluetooth not supported on the device";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Bluetooth is supported";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        //turn bluetooth on if disabled
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
            System.out.println("bleutooth was not enabled but now is enabled");
        }else{
            System.out.println("bleutooth enabled");
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            System.out.println("looking for the deice)");
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                mDevice = device;
                System.out.println("device connected/paired)");
                System.out.println(mDevice);
            }
        }

        //sets flags default values
        menuVisible = false;
        filterMenuVisible = false;
        mode = 0; // 0 = none, 1 = brightness, 2 = blur
        brightness = 0;
        blur = 0;
        lastButtonPressed = null;


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

        mConnectThread = new ConnectThread(mDevice);
        mConnectThread.start();



    } // end onCreate

    public void changeBgNormal() { RL.setBackgroundResource(R.drawable.bg_normal); }
    public void changeBgNormal2() { RL.setBackgroundResource(R.drawable.bg_normal); }
    public void changeBgBlackWhite() { RL.setBackgroundResource(R.drawable.bg_blackwhite); }
    public void changeBgSepia() { RL.setBackgroundResource(R.drawable.bg_sepia); }
    public void changeBgHarsh() { RL.setBackgroundResource(R.drawable.bg_harsh); }
    public void changeBgVintage() { RL.setBackgroundResource(R.drawable.bg_vintage); }
    public void changeBgBlur1() { RL.setBackgroundResource(R.drawable.bg_blur1); }
    public void changeBgBlur2() { RL.setBackgroundResource(R.drawable.bg_blur2); }
    public void changeBgBright1() { RL.setBackgroundResource(R.drawable.bg_bright1); }
    public void changeBgBright2() { RL.setBackgroundResource(R.drawable.bg_bright2); }
    public void changeBgBright3() { RL.setBackgroundResource(R.drawable.bg_bright3); }
    public void changeBgBright4() { RL.setBackgroundResource(R.drawable.bg_bright4); }


    // change to the filter menu
    public void changeToFilterMenu(){
        filterMenuVisible = true;
        menuVisible = false;
        mode = 0;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MenuFragment filterFragment = new MenuFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, filterFragment, "FilterMenuFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // change to selected brightness icon and set flag for mode
    public void changeToBrightness(View v) {
        // change images
        if (lastButtonPressed == null) {
            lastButtonPressed = v;
        }
        else if (lastButtonPressed != v) {
            lastButtonPressed.setBackgroundResource(R.drawable.blur);
        }
        v.setBackgroundResource(R.drawable.brightness_selected);
        lastButtonPressed = v;
        //set flag
        mode = 1;
    }

    // change to selected blur icon and set flag for mode
    public void changeToBlur(View v) {
        // change images
        if (lastButtonPressed == null) {
            lastButtonPressed = v;
        }
        else if (lastButtonPressed != v) {
            lastButtonPressed.setBackgroundResource(R.drawable.brightness);
        }
        v.setBackgroundResource(R.drawable.blur_selected);
        lastButtonPressed = v;
        // set flag
        mode = 2;
    }

    // call this upon a side bend in
    public void bendIn() {
        if (menuVisible) {
            // do nothing
        } else if (!menuVisible && filterMenuVisible) { //filter menu is visible, return to main frgament
            //do nothing, it's already open

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
        //else do nothing

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


    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        ConnectedThread mConnectedThread;
        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }
        public void run() {
            mBluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }
            mConnectedThread = new ConnectedThread(mmSocket);
            mConnectedThread.start();
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            final byte[] buffer = new byte[3];
            int begin = 0;
            int bytes = 0;
            while (true) {
                try {
                    bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
                    for(int i = begin; i < bytes; i++) {
                        if(buffer[i] == "#".getBytes()[0]) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    doAction(buffer);
                                }
                            });
                            ;
                            //mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                            begin = i + 1;
                            if(i == bytes - 1) {
                                bytes = 0;
                                begin = 0;
                            }
                        }
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void doAction(byte[] buffer){
            if (buffer[0] == 1){ //bend in
                bendIn();
            }
            else if(buffer[0] == 2){//bend out
                bendOut();
            }
            if(buffer[1] == 1){
                dogEarIn();
            }
            else if(buffer[1] == 2){
                dogEarOut();
            }
        }

    }
    /*
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            byte[] writeBuf = (byte[]) msg.obj;
            int begin = (int)msg.arg1;
            int end = (int)msg.arg2;

            switch(msg.what) {
                case 1:
                    String writeMessage = new String(writeBuf);
                    writeMessage = writeMessage.substring(begin, end);
                    System.out.println(writeMessage);
                    break;
            }
        }
    };
    */

}
