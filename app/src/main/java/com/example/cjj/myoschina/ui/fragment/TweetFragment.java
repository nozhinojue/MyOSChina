package com.example.cjj.myoschina.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cjj.myoschina.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TweetFragment extends Fragment {


    public TweetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet, container, false);
    }

}
