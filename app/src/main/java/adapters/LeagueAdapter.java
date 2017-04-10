package adapters;

import android.content.Context;
import android.media.Image;
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
import models.League;

import static com.perspective.prime.utras.R.id.awayGoals;
import static com.perspective.prime.utras.R.id.homeGoals;
import static com.perspective.prime.utras.R.id.status;

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
        League currentLeague = getItem(position);

        String LeagueName = currentLeague.getLeagueName();
        int LeagueLogo = currentLeague.getLeagueLogo();

        ImageView leagueLogo = (ImageView) listItemView.findViewById(R.id.leagueLogo);
        leagueLogo.setImageResource(LeagueLogo);

        return listItemView;
    }
}
