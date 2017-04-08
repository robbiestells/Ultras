package adapters;

import android.content.Context;
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

import java.util.ArrayList;

import models.Fixture;

/**
 * Created by rob on 4/7/17.
 */

public class FixtureAdapter extends ArrayAdapter<Fixture>{
    public FixtureAdapter(Context context, ArrayList<Fixture> fixtures){
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

        TextView HomeTeamTV = (TextView) listItemView.findViewById(R.id.homeTeam);
        HomeTeamTV.setText(currentFixture.getHomeTeam());

        TextView AwayTeamTV = (TextView) listItemView.findViewById(R.id.awayTeam);
        AwayTeamTV.setText(currentFixture.getAwayTeam());

        TextView HomeGoals = (TextView) listItemView.findViewById(R.id.homeGoals);
        HomeGoals.setText(currentFixture.getHomeGoals());

        TextView AwayGoals = (TextView) listItemView.findViewById(R.id.awayGoals);
        AwayGoals.setText(currentFixture.getAwayGoals());

        TextView Status = (TextView) listItemView.findViewById(R.id.status);
        Status.setText(currentFixture.getStatus());

        return listItemView;
    }
}
