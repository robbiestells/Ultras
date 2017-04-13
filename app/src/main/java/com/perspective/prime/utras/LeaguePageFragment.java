package com.perspective.prime.utras;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import adapters.LeagueAdapter;
import models.League;

/**
 * Created by rob on 4/12/17.
 */

public class LeaguePageFragment extends Fragment {
    View lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lv = inflater.inflate(R.layout.fragment_league_page, container, false);

       // Toolbar toolbar = (Toolbar) lv.findViewById(R.id.toolbar);
        //((ActionBarActivity)getActivity()).getSupportActionBar(toolbar);

//        TabLayout tabLayout = (TabLayout) lv.findViewById(R.id.tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setText("Table"));
//        tabLayout.addTab(tabLayout.newTab().setText("Scores"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        final ViewPager viewPager = (ViewPager) lv.findViewById(R.id.pager);
//        final PagerAdapter adapter = new PagerAdapter
//                (getFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });




        return lv;
    }

}