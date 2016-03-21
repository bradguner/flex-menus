package com.flex.menus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragView = inflater.inflate(R.layout.fragment_menu, container, false);
        final MainActivity activity = (MainActivity) getActivity();

        fragView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float y = event.getRawY();

                    //Flexible phone dimensions
                    float top = 100;
                    float bottom = 1000;
                    if (y < bottom && y > top) {
                        if (y < 180) {
                            activity.changeBgBlackWhite();
                        }
                        if (y > 196 && y < 300) {
                            activity.changeBgSepia();
                        }
                        if (y > 310 && y < 430) {
                            activity.changeBgNormal();
                        }
                        if (y > 440 && y < 550) {
                            activity.changeBgVintage();
                        }
                        if (y > 560 && y < 661) {
                            activity.changeBgHarsh();
                        }
                    }


                    /*
                    //Erin's Phone dimensions
                    float top = 100;
                    float bottom = 1000;
                    if (y < bottom && y > top) {
                        if (y < 270) {
                            activity.changeBgBlackWhite();
                        }
                        if (y > 270 && y < 450) {
                            activity.changeBgSepia();
                        }
                        if (y > 450 && y < 678) {
                            activity.changeBgNormal();
                        }
                        if (y > 678 && y < 853) {
                            activity.changeBgVintage();
                        }
                        if (y > 853 && y < 1020) {
                            activity.changeBgHarsh();
                        }
                    }
                    */
                }

                return true;
            }
        });
        // Inflate the layout for this fragment
        return fragView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
