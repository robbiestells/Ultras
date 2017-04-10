package adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.perspective.prime.utras.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import models.Fixture;

import static com.perspective.prime.utras.R.id.status;

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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM");
            Date testDate = null;
            try {
                testDate = sdf.parse(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
            status = formatter.format(testDate);
        } else if(status.contains("PLAY")){
            status = "";
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

        ImageView HomeTeamCrest = (ImageView) listItemView.findViewById(R.id.homeTeamCrest);
        int homeCrest = getContext().getResources().getIdentifier(currentFixture.getHomeTeamId(), "drawable", getContext().getPackageName());
        HomeTeamCrest.setImageResource(homeCrest);

        ImageView AwayTeamCrest = (ImageView) listItemView.findViewById(R.id.awayeTeamCrest);
        int awayCrest = getContext().getResources().getIdentifier(currentFixture.getAwayTeamId(), "drawable", getContext().getPackageName());
        AwayTeamCrest.setImageResource(awayCrest);

        return listItemView;
    }
}
