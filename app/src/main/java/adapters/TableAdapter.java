package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.perspective.prime.utras.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import models.Fixture;
import models.TableClub;

import static android.R.attr.gravity;
import static android.R.attr.id;
import static android.R.attr.layout_gravity;
import static android.R.attr.layout_height;
import static android.R.attr.layout_marginStart;
import static android.R.attr.layout_weight;
import static android.R.attr.layout_width;
import static com.perspective.prime.utras.R.id.awayGoals;
import static com.perspective.prime.utras.R.id.homeGoals;
import static com.perspective.prime.utras.R.id.status;

/**
 * Created by rob on 4/13/17.
 */

public class TableAdapter extends ArrayAdapter<TableClub> {
    public TableAdapter(Context context, ArrayList<TableClub> tableClubs) {
        super(context, 0, tableClubs);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_table_club, parent, false);
        }

        // Get the host and load the name and image
        TableClub currentClub = getItem(position);

        String rank = currentClub.getRank();
        String clubName = currentClub.getTeam();
        String played = currentClub.getPlayedGames();
        String wins = currentClub.getWins();
        String draws = currentClub.getDraws();
        String losses = currentClub.getLosses();
        String goalDifference = currentClub.getGoalDifference();
        String points = currentClub.getPoints();
        String logoId = "a" + currentClub.getTeamId();



        TextView Rank = (TextView) listItemView.findViewById(R.id.position);
        Rank.setText(rank);

        TextView ClubName = (TextView) listItemView.findViewById(R.id.clubName);
        ClubName.setText(clubName);

        TextView Played = (TextView) listItemView.findViewById(R.id.played);
        Played.setText(played);

        TextView Wins = (TextView) listItemView.findViewById(R.id.wins);
        Wins.setText(wins);

        TextView Losses = (TextView) listItemView.findViewById(R.id.losses);
        Losses.setText(losses);

        TextView Draws = (TextView) listItemView.findViewById(R.id.draws);
        Draws.setText(draws);

        TextView GoalDifference = (TextView) listItemView.findViewById(R.id.goalDifference);
        GoalDifference.setText(goalDifference);

        TextView Points = (TextView) listItemView.findViewById(R.id.points);
        Points.setText(points);

        ImageView Logo = (ImageView) listItemView.findViewById(R.id.logo);

        if (logoId != null) {
            int clubLogo = getContext().getResources().getIdentifier(logoId, "drawable", getContext().getPackageName());
            Logo.setImageResource(clubLogo);
        }

        return listItemView;
    }
}