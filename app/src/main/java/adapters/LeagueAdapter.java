package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.perspective.prime.ultras.R;

import java.util.ArrayList;

import models.League;

/**
 * Created by rob on 4/10/17.
 */

public class LeagueAdapter extends ArrayAdapter<League> {
    public LeagueAdapter(Context context, ArrayList<League> leagues) {
        super(context, 0, leagues);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_league, parent, false);
        }

        // Get the host and load the name and image
        final League currentLeague = getItem(position);

        String LeagueName = currentLeague.getLeagueName();
        int LeagueLogo = currentLeague.getLeagueLogo();

        ImageView leagueLogo = (ImageView) listItemView.findViewById(R.id.leagueLogo);
        leagueLogo.setImageResource(LeagueLogo);

        return listItemView;
    }
}
