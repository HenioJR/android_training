package com.example.henio.sunshine.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ArrayList<String> mock = new ArrayList<>();
        mock.add("Today - Sunny - 88/63");
        mock.add("Tomorrow - Foggy - 70/46");
        mock.add("Weds - Cloudy - 72/63");
        mock.add("Thurs - Rainy - 64/51");
        mock.add("Fri - Foggy - 70/47");
        mock.add("Sat - Sunny - 76/68");

        return rootView;
    }
}
