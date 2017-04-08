package com.perspective.prime.utras;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import adapters.FixtureAdapter;
import models.Fixture;

/**
 * Created by rob on 4/7/17.
 */

public class ScoresFragment extends Fragment {
    private ArrayList<Fixture> fixtures = new ArrayList<>();
    ListView fixtureList;
    ArrayAdapter<Fixture> fixtureAdapter;
    View rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rv = inflater.inflate(R.layout.fragment_scores, container, false);

        fixtureList = (ListView) rv.findViewById(R.id.fixtureList);

        getData();

        return rv;
    }

    private void getData() {
        new GetFixtures(getActivity()).execute();
    }

    public class GetFixtures extends AsyncTask<Void, Void, ArrayList<Fixture>> {
        private static final String LOG_TAG = "ASYNC ";
        // ArrayList<Fixture> fixtures;
        Context context;

        public GetFixtures(Context fragment) {
            this.context = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //TODO check for internet connection

            Toast toast = Toast.makeText(getContext(), "Getting Fixtures", Toast.LENGTH_SHORT);
            toast.show();

        }

        @Override
        protected void onPostExecute(ArrayList<Fixture> fixtures) {
            super.onPostExecute(fixtures);

            Toast toast = Toast.makeText(getContext(), "Fixtures updated", Toast.LENGTH_SHORT);
            toast.show();

            fixtureList.setAdapter(new FixtureAdapter(getActivity().getApplicationContext(), fixtures));
        }

        @Override
        protected ArrayList<Fixture> doInBackground(Void... params) {

            String feed = "http://api.football-data.org/v1/fixtures?timeFrame=n1";
            URL url = createUrl(feed);
            String jsonResponse;
            try {
                jsonResponse = makeHttpRequest(url);
                fixtures = extractFeatureFromJson(jsonResponse);
            } catch (IOException e) {
                Log.e("ASYNC: ", "Problem making the HTTP request.", e);
            }

            return fixtures;
        }

        public ArrayList<Fixture> extractFeatureFromJson(String fixtureJson) {
            if (TextUtils.isEmpty(fixtureJson)) {
                return null;
            }

            ArrayList<Fixture> fixtures = new ArrayList<>();

            try {
                JSONObject baseJsonResponse = new JSONObject(fixtureJson);

                JSONArray fixtureArray = baseJsonResponse.getJSONArray("fixtures");

                // For each fixture in the array, create a Fixture object and add it to the ArrayList
                for (int i = 0; i < fixtureArray.length(); i++) {
                    JSONObject currentFixture = fixtureArray.getJSONObject(i);

                    //get Fixture info
                    String date = currentFixture.getString("date");
                    String status = currentFixture.getString("status");
                    String homeTeam = currentFixture.getString("homeTeamName");
                    String awayTeam = currentFixture.getString("awayTeamName");

                    //get other info
                    String homeGoals = "";
                    String awayGoals = "";
                    String competition = "";

                    JSONObject resultArray = currentFixture.getJSONObject("result");

                    homeGoals = resultArray.getString("goalsHomeTeam");
                    awayGoals = resultArray.getString("goalsAwayTeam");

                    JSONObject linkArray = currentFixture.getJSONObject("_links");

                    JSONObject competitionArray = linkArray.getJSONObject("competition");
                    competition = competitionArray.getString("href");

                    // Create a new Fixture object
                    Fixture fixture = new Fixture(date, status, homeTeam, awayTeam, homeGoals, awayGoals, competition);

                    //add feed item to list
                    fixtures.add(fixture);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            }
            //return movie list
            return fixtures;
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";

            if (url == null) {
                return jsonResponse;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "Http response code: " + urlConnection.getResponseCode());
                }

            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem getting json");

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        //create URL out of string
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e("ASYNC: ", "ERROR with creating URL", exception);
                return null;
            }
            return url;
        }

//        private void saveFeedItems(ArrayList<FeedItem> feedItems){
//            FeedDbHelper mDbHelper = new FeedDbHelper(context);
//            SQLiteDatabase db = mDbHelper.getWritableDatabase();
//
//            String query = "SELECT * FROM " + FeedContract.FeedEntry.TABLE_NAME + " WHERE " + FeedContract.FeedEntry.COLUMN_EPIOSDE_AUDIO
//                    + " =?";
//
//            for (FeedItem item:feedItems) {
//
//                Cursor cursor = db.rawQuery(query, new String[] {item.getAudioUrl()}) ;
//
//                if (cursor.getCount() <= 0){
//                    //get values
//                    ContentValues values = new ContentValues();
//                    values.put(FeedContract.FeedEntry.COLUMN_SHOW_NAME, item.getShow());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPISODE_TITLE, item.getTitle());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPIOSDE_LINK, item.getLink());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPISODE_DESCRIPTION, item.getDescription());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPISODE_DATE, item.getPubDate());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPIOSDE_LENGTH, item.getLength());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPIOSDE_AUDIO, item.getAudioUrl());
//
//                    //insert a new entry with the data above
//                    long newRowId = db.insert(FeedContract.FeedEntry.TABLE_NAME, null, values);
//                    Log.v("Insert Feed item", "New row ID: " + newRowId);
//                }
//                cursor.close();
//            }
//
//            db.close();
//        }
    }

}
