package com.perspective.prime.utras;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import adapters.FixtureAdapter;
import adapters.LeagueAdapter;
import models.League;

/**
 * Created by rob on 4/7/17.
 */

public class LeaguesFragment extends Fragment {
    View lv;
    GridView LeagueGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lv = inflater.inflate(R.layout.fragment_leagues, container, false);

        LeagueGridView = (GridView) lv.findViewById(R.id.leagueGrid);

        ArrayList<League> leagues = new ArrayList<>();

        leagues.add(new League("Premier League", R.drawable.epl));
        leagues.add(new League("Serie A", R.drawable.seriea));

        LeagueGridView.setAdapter(new LeagueAdapter(getActivity().getApplicationContext(), leagues));

        return lv;
    }
}