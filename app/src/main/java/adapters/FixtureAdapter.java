package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.perspective.prime.ultras.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import models.Fixture;

/**
 * Created by rob on 4/7/17.
 */

public class FixtureAdapter extends ArrayAdapter<Fixture> {
    public FixtureAdapter(Context context, ArrayList<Fixture> fixtures) {
        super(context, 0, fixtures);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_fixture, parent, false);
        }

        // Get the host and load the name and image
        Fixture currentFixture = getItem(position);

        String status = currentFixture.getStatus();
        String homeGoals = "0";
        String awayGoals = "0";

        if (status.contains("FINISHED")) {
            status = "FT";
            homeGoals = currentFixture.getHomeGoals();
            awayGoals = currentFixture.getAwayGoals();
        } else if (status.contains("TIMED")) {
            String date = currentFixture.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date testDate = null;
            try {
                testDate = sdf.parse(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
            status = formatter.format(testDate);
        } else if (status.contains("PLAY")) {
            status = "Live";
            homeGoals = currentFixture.getHomeGoals();
            awayGoals = currentFixture.getAwayGoals();
        }

        TextView Status = (TextView) listItemView.findViewById(R.id.status);
        Status.setText(status);

        TextView HomeTeamTV = (TextView) listItemView.findViewById(R.id.homeTeam);
        HomeTeamTV.setText(currentFixture.getHomeTeam());

        TextView AwayTeamTV = (TextView) listItemView.findViewById(R.id.awayTeam);
        AwayTeamTV.setText(currentFixture.getAwayTeam());

        TextView HomeGoals = (TextView) listItemView.findViewById(R.id.homeGoals);
        HomeGoals.setText(homeGoals);

        TextView AwayGoals = (TextView) listItemView.findViewById(R.id.awayGoals);
        AwayGoals.setText(awayGoals);

        TextView LeagueName = (TextView) listItemView.findViewById(R.id.leagueName);
        String leagueName;
        if (currentFixture.getCompetition().contains("426")) {
            leagueName = "Premier League";
        } else if (currentFixture.getCompetition().contains("436")) {
            leagueName = "La Liga";
        } else if (currentFixture.getCompetition().contains("431")) {
            leagueName = "Bundesliga";
        } else if (currentFixture.getCompetition().contains("438")) {
            leagueName = "Serie A";
        } else if (currentFixture.getCompetition().contains("440")) {
            leagueName = "Champions League";
        }
        else {
            leagueName = "Other";
        }

        LeagueName.setText(leagueName);

        ImageView HomeTeamCrest = (ImageView) listItemView.findViewById(R.id.homeTeamCrest);
        ImageView AwayTeamCrest = (ImageView) listItemView.findViewById(R.id.awayTeamCrest);

        if (currentFixture.getHomeTeamId() != null){
            int homeCrest = getContext().getResources().getIdentifier(currentFixture.getHomeTeamId(), "drawable", getContext().getPackageName());
            HomeTeamCrest.setImageResource(homeCrest);
        }
        if (currentFixture.getAwayTeamId() != null){
            int homeCrest = getContext().getResources().getIdentifier(currentFixture.getHomeTeamId(), "drawable", getContext().getPackageName());
            HomeTeamCrest.setImageResource(homeCrest);
            int awayCrest = getContext().getResources().getIdentifier(currentFixture.getAwayTeamId(), "drawable", getContext().getPackageName());
            AwayTeamCrest.setImageResource(awayCrest);
        }



        return listItemView;
    }
}
