package com.perspective.prime.ultras;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import adapters.TableAdapter;
import models.TableClub;

/**
 * Created by rob on 4/12/17.
 */

public class TableFragment extends Fragment {
    View tv;
    ArrayList<TableClub> tableClubs;
    String leagueId;
    ListView tableListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tv = inflater.inflate(R.layout.fragment_table, container, false);

        Bundle b = getArguments();
        if (b != null) {
            String league = b.getString("league");
            if (league == "Premier League") {
                leagueId = "426";
            } else if (league == "Serie A") {
                leagueId = "438";
            } else {
                leagueId = "308";
            }
        } else {
            leagueId = "308";
        }
        tableListView = (ListView) tv.findViewById(R.id.tableListView);

        getData();

        return tv;
    }

    private void getData() {
        new GetTable(getActivity()).execute();
    }

    public class GetTable extends AsyncTask<Void, Void, ArrayList<TableClub>> {
        private static final String LOG_TAG = "ASYNC ";
        Context context;

        public GetTable(Context fragment) {
            this.context = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //TODO check for internet connection

            Toast toast = Toast.makeText(getContext(), "Getting Table", Toast.LENGTH_SHORT);
            toast.show();

        }

        @Override
        protected void onPostExecute(ArrayList<TableClub> tableClubs) {
            super.onPostExecute(tableClubs);

            displayTable();
        }


        @Override
        protected ArrayList<TableClub> doInBackground(Void... params) {

            String feed = "http://api.football-data.org/v1/competitions/" + leagueId + "/leagueTable";
            URL url = createUrl(feed);
            String jsonResponse;
            try {
                jsonResponse = makeHttpRequest(url);
                tableClubs = extractFeatureFromJson(jsonResponse);
            } catch (IOException e) {
                Log.e("ASYNC: ", "Problem making the HTTP request.", e);
            }

            return tableClubs;
        }

        public ArrayList<TableClub> extractFeatureFromJson(String tableJson) {
            if (TextUtils.isEmpty(tableJson)) {
                return null;
            }

            ArrayList<TableClub> tableClubs = new ArrayList<>();

            try {
                JSONObject baseJsonResponse = new JSONObject(tableJson);

                JSONArray standingArray = baseJsonResponse.getJSONArray("standing");

                // For each fixture in the array, create a Fixture object and add it to the ArrayList
                for (int i = 0; i < standingArray.length(); i++) {
                    JSONObject currentClub = standingArray.getJSONObject(i);

                    //get Club info
                    String rank = currentClub.getString("position");
                    String team = currentClub.getString("teamName");
                    String playedGames = currentClub.getString("playedGames");
                    String points = currentClub.getString("points");
                    String goals = currentClub.getString("goals");
                    String goalsAgainst = currentClub.getString("goalsAgainst");
                    String goalDifference = currentClub.getString("goalDifference");
                    String wins = currentClub.getString("wins");
                    String draws = currentClub.getString("draws");
                    String losses = currentClub.getString("losses");


                    String teamId;
                    JSONObject linkArray = currentClub.getJSONObject("_links");

                    JSONObject teamArray = linkArray.getJSONObject("team");
                    teamId = teamArray.getString("href");

                    String[] teamSplit = teamId.split("/");
                    int teamInt = teamSplit.length;
                    teamId = teamSplit[teamInt - 1];


                    // Create a new Club object
                    TableClub tableClub = new TableClub(rank, team, playedGames, points, goals, goalsAgainst, goalDifference, wins, draws, losses, teamId);

                    //add feed item to list
                    tableClubs.add(tableClub);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            }
            //return movie list
            return tableClubs;
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
                urlConnection.setRequestProperty("X-Auth-Token", "cc99d6b0d1024981b61b21fb606e4a48");
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


        private void displayTable() {
            if (tableClubs != null){
                tableListView.setAdapter(new TableAdapter(getActivity().getApplicationContext(), tableClubs));
            }

        }
    }
}
