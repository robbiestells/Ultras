package adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.perspective.prime.utras.LeaguePageFragment;
import com.perspective.prime.utras.LeaguesFragment;
import com.perspective.prime.utras.R;
import com.perspective.prime.utras.TableFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import models.Fixture;
import models.League;

import static android.R.attr.fragment;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.provider.AlarmClock.EXTRA_MESSAGE;
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
        final League currentLeague = getItem(position);

        String LeagueName = currentLeague.getLeagueName();
        int LeagueLogo = currentLeague.getLeagueLogo();

        ImageView leagueLogo = (ImageView) listItemView.findViewById(R.id.leagueLogo);
        leagueLogo.setImageResource(LeagueLogo);

        final Context context = parent.getContext();

        leagueLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new TableFragment();

                Bundle args = new Bundle();
                args.putString("league", currentLeague.getLeagueName());
                fragment.setArguments(args);
                FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });

        return listItemView;
    }
}
