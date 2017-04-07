package adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.perspective.prime.utras.R;

import java.util.ArrayList;

import models.Fixture;

/**
 * Created by rob on 4/7/17.
 */

public class FixtureAdapter extends RecyclerView.Adapter<ViewHolder> {
    ArrayList<Fixture> fixtures;
    Context context;

    public FixtureAdapter(Context context, ArrayList<Fixture> fixtures){
        this.fixtures = fixtures;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View fixtureView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fixture, parent, false);
        FixtureViewHolder holder=new FixtureViewHolder(fixtureView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Fixture current = fixtures.get(position);
        ((FixtureViewHolder) holder).bindData(current);
    }

//    public class FixtureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public class FixtureViewHolder extends RecyclerView.ViewHolder {
        TextView HomeTeam,AwayTeam,HomeTeamScore,AwayTeamScore,Status;
        CardView cardView;
        public FixtureViewHolder(View itemView) {
            super(itemView);

//            itemView.setOnClickListener(this);

            HomeTeam= (TextView) itemView.findViewById(R.id.homeTeam);
            AwayTeam = (TextView) itemView.findViewById(R.id.awayTeam);
            HomeTeamScore= (TextView) itemView.findViewById(R.id.homeGoals);
            AwayTeamScore = (TextView) itemView.findViewById(R.id.awayGoals);
            Status = (TextView) itemView.findViewById(R.id.status);
            cardView= (CardView) itemView.findViewById(R.id.fixtureItemCard);
        }
    //        @Override
//        public void onClick(View view) {
//            //get episode that was clicked on
//            FeedItem selected = feedItems.get(getPosition());
//
//            //pass this episode to Episode Page fragment
//            if (callback != null){
//                callback.onItemClicked(selected);
//            }
//        }

    public void bindData(Fixture current) {
        HomeTeam.setText(current.getHomeTeam());
        AwayTeam.setText(current.getAwayTeam());
        HomeTeamScore.setText(current.getHomeGoals());
        AwayTeamScore.setText(current.getAwayGoals());
        Status.setText(current.getStatus());
    }


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
