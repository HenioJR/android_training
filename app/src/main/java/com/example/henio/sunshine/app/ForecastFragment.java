package com.example.henio.sunshine.app;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private final String LOG_TAG = ForecastFragment.class.getSimpleName();

    public ForecastFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //called to indicate that the fragment would like to add items to the Options Menu.
        //otherwise, the fragment will not receive a call to onCreateOptionsMenu()
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(LOG_TAG, "item refresh clicked");
        if(item.getItemId() == R.id.action_refresh){
            new FetchWeatherTask().execute("Florianopolis");
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public class FetchWeatherTask extends AsyncTask<String, Void, Void>{

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(String... params) {
            if(params.length == 0){
                return null;
            }
            return this.httpConnection(params[0]);
        }

        private Void httpConnection(String cityName){
            // these two need to be closed after
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";
                final String APPID_PARAM = "APPID";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, cityName)
                        .appendQueryParameter(FORMAT_PARAM, "json")
                        .appendQueryParameter(UNITS_PARAM, "metric")
                        .appendQueryParameter(DAYS_PARAM, "7")
                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_WEATHER_MAP_APPID_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Url = " + url);

                //openning connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //read the answer
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                //return null if server return nothing
                if(inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }
                //return null if server returned an empty response
                if(buffer.length() == 0){
                    return null;
                }

                //if everything ok, return json
                String jsonResponse = buffer.toString();

            } catch (IOException e){
                Log.e(LOG_TAG, "Error", e);
                return null;

            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }

                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error on close BufferedReader", e);
                    }
                }
                return null;
            }
        }
    }
}
