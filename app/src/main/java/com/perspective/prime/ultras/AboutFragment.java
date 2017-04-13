package com.perspective.prime.ultras;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rob on 4/7/17.
 */

public class AboutFragment extends Fragment {
    View av;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        av = inflater.inflate(R.layout.fragment_about, container, false);

        TextView AboutTV = (TextView) av.findViewById(R.id.aboutText);
        AboutTV.setText("The Ultras app was made by Rob Steller using the football-data.org apis. To see more of my work, check out ThePrimePerspective.com");

        return av;
    }
}