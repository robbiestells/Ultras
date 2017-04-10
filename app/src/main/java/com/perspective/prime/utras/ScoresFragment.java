package com.perspective.prime.utras;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import adapters.FixtureAdapter;
import data.UltraContract;
import data.UltraContract.FixtureEntry;
import data.UltraDbHelper;
import models.Fixture;

import static android.R.attr.format;
import static com.perspective.prime.utras.R.id.awayGoals;
import static com.perspective.prime.utras.R.id.awayTeam;
import static com.perspective.prime.utras.R.id.homeGoals;
import static com.perspective.prime.utras.R.id.homeTeam;

/**
 * Created by rob on 4/7/17.
 */

public class ScoresFragment extends Fragment {
    private ArrayList<Fixture> fixtures = new ArrayList<>();
    private ArrayList<Fixture> englishFixtures = new ArrayList<>();
    private ArrayList<Fixture> italianFixtures = new ArrayList<>();
    ListView fixtureList, italianList, germanList, englishList, spainList;
    TextView dateFilter;
    ArrayAdapter<Fixture> fixtureAdapter;
    View rv;
    Button NextButton;
    Button PreviousButton;
    String currentDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rv = inflater.inflate(R.layout.fragment_scores, container, false);

        //fixtureList = (ListView) rv.findViewById(R.id.fixtureList);
        italianList = (ListView) rv.findViewById(R.id.ItalianFixtureList);
//        germanList = (ListView) rv.findViewById(R.id.GermanFixtureList);
        englishList = (ListView) rv.findViewById(R.id.EnglishFixtureList);
//        spainList = (ListView) rv.findViewById(R.id.S);

        dateFilter = (TextView) rv.findViewById(R.id.DateFilter);

        PreviousButton = (Button) rv.findViewById(R.id.PreviousButton);
        NextButton = (Button) rv.findViewById(R.id.NextButton);

        //get date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = df.format(c.getTime());
        dateFilter.setText(currentDate);

        getData();

        fixtures = getSavedFixtures(getContext(), currentDate);
        filterData(currentDate);

        PreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(currentDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, -1);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                currentDate = sdf1.format(c.getTime());
                filterData(currentDate);
                dateFilter.setText(currentDate);
            }
        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(currentDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, 1);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                currentDate = sdf1.format(c.getTime());
                filterData(currentDate);
                dateFilter.setText(currentDate);
            }
        });

        return rv;
    }

    private void filterData(String date) {
        italianFixtures.clear();
        englishFixtures.clear();

        for (Fixture fixture : fixtures) {
            if (fixture.getDate().contains(date)) {
                if (fixture.getCompetition().contains("438")) {
                    italianFixtures.add(fixture);
                }
            }
            if (fixture.getDate().contains(date)) {
                if (fixture.getCompetition().contains("426")) {
                    englishFixtures.add(fixture);
                }
            }
        }

        if (italianFixtures.size() != 0) {
            italianList.setVisibility(View.VISIBLE);
            italianList.setAdapter(new FixtureAdapter(getActivity().getApplicationContext(), italianFixtures));
        } else {
            italianList.setVisibility(View.INVISIBLE);
        }
        if (englishFixtures.size() != 0) {
            englishList.setVisibility(View.VISIBLE);
            englishList.setAdapter(new FixtureAdapter(getActivity().getApplicationContext(), englishFixtures));
        } else {
            englishList.setVisibility(View.INVISIBLE);
        }
    }

    private void getData() {
        new GetFixtures(getActivity()).execute();
    }

    public class GetFixtures extends AsyncTask<Void, Void, ArrayList<Fixture>> {
        private static final String LOG_TAG = "ASYNC ";
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

            saveFixtures(fixtures);
            filterData(currentDate);
        }

        @Override
        protected ArrayList<Fixture> doInBackground(Void... params) {

            String feed = "http://api.football-data.org/v1/fixtures?timeFrame=" + "n" + "99";
            URL url = createUrl(feed);
            String jsonResponse;
            try {
                jsonResponse = makeHttpRequest(url);
                fixtures = extractFeatureFromJson(jsonResponse);
            } catch (IOException e) {
                Log.e("ASYNC: ", "Problem making the HTTP request.", e);
            }

            ArrayList<Fixture> backFixtures = new ArrayList<>();
            String feedBack = "http://api.football-data.org/v1/fixtures?timeFrame=" + "p" + "99";
            URL urlBack = createUrl(feedBack);
            String jsonResponseBack;
            try {
                jsonResponseBack = makeHttpRequest(urlBack);
                backFixtures = extractFeatureFromJson(jsonResponseBack);
            } catch (IOException e) {
                Log.e("ASYNC: ", "Problem making the Back HTTP request.", e);
            }

            for (Fixture fixture : backFixtures) {
                fixtures.add(fixture);
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
                    String fixtureId = "";
                    String homeGoals = "";
                    String awayGoals = "";
                    String competition = "";
                    String homeTeamId = "";
                    String awayTeamId = "";

                    JSONObject resultArray = currentFixture.getJSONObject("result");

                    homeGoals = resultArray.getString("goalsHomeTeam");
                    awayGoals = resultArray.getString("goalsAwayTeam");

                    JSONObject linkArray = currentFixture.getJSONObject("_links");

                    JSONObject competitionArray = linkArray.getJSONObject("competition");
                    competition = competitionArray.getString("href");

                    JSONObject selfArray = linkArray.getJSONObject("self");
                    fixtureId = selfArray.getString("href");

                    JSONObject homeArray = linkArray.getJSONObject("homeTeam");
                    homeTeamId = homeArray.getString("href");

                    JSONObject awayArray = linkArray.getJSONObject("awayTeam");
                    awayTeamId = awayArray.getString("href");

                    Date DateTime = new Date();

                    SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    try {
                        DateTime = sourceFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    destFormat.setTimeZone(TimeZone.getDefault());
                    String localDate = destFormat.format(DateTime);

                    String[] compSplit = competition.split("/");
                    int compInt = compSplit.length;
                    competition = compSplit[compInt - 1];

                    String[] fixtureSplit = fixtureId.split("/");
                    int fixtureInt = fixtureSplit.length;
                    fixtureId = fixtureSplit[fixtureInt - 1];

                    String[]  homeIdSplit = homeTeamId.split("/");
                    int homeInt = homeIdSplit.length;
                    homeTeamId = "a" + homeIdSplit[homeInt - 1];

                    String[]  awayIdSplit = awayTeamId.split("/");
                    int awayInt = awayIdSplit.length;
                    awayTeamId = "a" + awayIdSplit[awayInt - 1];

                    // Create a new Fixture object
                    Fixture fixture = new Fixture(fixtureId, localDate, status, homeTeam, awayTeam, homeGoals, awayGoals, competition, homeTeamId, awayTeamId);

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

        private void saveFixtures(ArrayList<Fixture> fixtures) {
            UltraDbHelper mDbHelper = new UltraDbHelper(context);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            for (Fixture item : fixtures) {

                String query = "SELECT * FROM " + FixtureEntry.TABLE_NAME + " WHERE " + FixtureEntry.COLUMN_FIXTURE_ID
                        + " =" + item.getFixtureId();
                Cursor cursor = db.rawQuery(query, new String[]{});

                if (cursor.moveToFirst()) {
                    //get values
                    ContentValues values = new ContentValues();
                    values.put(FixtureEntry.COLUMN_FIXTURE_ID, item.getFixtureId());
                    values.put(FixtureEntry.COLUMN_FIXTURE_AWAY_GOALS, item.getAwayGoals());
                    values.put(FixtureEntry.COLUMN_FIXTURE_AWAY_TEAM, item.getAwayTeam());
                    values.put(FixtureEntry.COLUMN_FIXTURE_BOME_GOALS, item.getHomeGoals());
                    values.put(FixtureEntry.COLUMN_FIXTURE_COMPETITION, item.getCompetition());
                    values.put(FixtureEntry.COLUMN_FIXTURE_DATE, item.getDate());
                    values.put(FixtureEntry.COLUMN_FIXTURE_HOME_TEAM, item.getHomeTeam());
                    values.put(FixtureEntry.COLUMN_FIXTURE_STATUS, item.getStatus());

                    //insert a new entry with the data above
                    long newRowId = db.insert(FixtureEntry.TABLE_NAME, null, values);
                    Log.v("Insert Fixture item", "New row ID: " + newRowId);
                } else {
                    //get values
                    ContentValues values = new ContentValues();
                    values.put(FixtureEntry.COLUMN_FIXTURE_ID, item.getFixtureId());
                    values.put(FixtureEntry.COLUMN_FIXTURE_AWAY_GOALS, item.getAwayGoals());
                    values.put(FixtureEntry.COLUMN_FIXTURE_AWAY_TEAM, item.getAwayTeam());
                    values.put(FixtureEntry.COLUMN_FIXTURE_BOME_GOALS, item.getHomeGoals());
                    values.put(FixtureEntry.COLUMN_FIXTURE_COMPETITION, item.getCompetition());
                    values.put(FixtureEntry.COLUMN_FIXTURE_DATE, item.getDate());
                    values.put(FixtureEntry.COLUMN_FIXTURE_HOME_TEAM, item.getHomeTeam());
                    values.put(FixtureEntry.COLUMN_FIXTURE_STATUS, item.getStatus());

                    long updateRowId = db.update(FixtureEntry.TABLE_NAME, values, null, null);
                    Log.v("Update Fixture item", "Update row ID: " + item.getFixtureId());
                }
                cursor.close();
            }

            db.close();
        }
    }

    public static ArrayList<Fixture> getSavedFixtures(Context context, String date) {
        UltraDbHelper mDbHelper = new UltraDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<Fixture> fixtures = new ArrayList<>();

        String query = "SELECT * FROM " + FixtureEntry.TABLE_NAME;
        //+ " WHERE " + FixtureEntry.COLUMN_FIXTURE_DATE + " =?";

        Cursor cursor = db.rawQuery(query, new String[]{});
        // Cursor cursor = db.rawQuery(query, new String[]{});

        int idColumnIndex = cursor.getColumnIndex(FixtureEntry.COLUMN_FIXTURE_ID);
        int dateColumnIndex = cursor.getColumnIndex(FixtureEntry.COLUMN_FIXTURE_DATE);
        int statusColumnIndex = cursor.getColumnIndex(FixtureEntry.COLUMN_FIXTURE_STATUS);
        int homeTeamColumnIndex = cursor.getColumnIndex(FixtureEntry.COLUMN_FIXTURE_HOME_TEAM);
        int awayTeamColumnIndex = cursor.getColumnIndex(FixtureEntry.COLUMN_FIXTURE_AWAY_TEAM);
        int homeGoalsColumnIndex = cursor.getColumnIndex(FixtureEntry.COLUMN_FIXTURE_BOME_GOALS);
        int awayGoalsColumnIndex = cursor.getColumnIndex(FixtureEntry.COLUMN_FIXTURE_AWAY_GOALS);
        int competitionColumnIndex = cursor.getColumnIndex(FixtureEntry.COLUMN_FIXTURE_COMPETITION);


        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Fixture item = new Fixture();
            item.setFixtureId(cursor.getString(idColumnIndex));
            item.setDate(cursor.getString(dateColumnIndex));
            item.setStatus(cursor.getString(statusColumnIndex));
            item.setHomeTeam(cursor.getString(homeTeamColumnIndex));
            item.setAwayTeam(cursor.getString(awayTeamColumnIndex));
            item.setHomeGoals(cursor.getString(homeGoalsColumnIndex));
            item.setAwayGoals(cursor.getString(awayGoalsColumnIndex));
            item.setCompetition(cursor.getString(competitionColumnIndex));
            fixtures.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return fixtures;
    }

}
