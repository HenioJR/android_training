package com.example.henio.sunshine.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        // adapter has four parameters: context, ID of list item layout, ID of text view to populate and list of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, mock);

        //ListView v = (ListView) getActivity().findViewById(R.id.listview_forecast);
        // we should use rootView instead of getActivity, pois rootView is closer to the listview_forecast than getActivity. It's about performance!
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(adapter);

        return rootView;
    }
}
