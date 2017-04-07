package models;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Time;

/**
 * Created by rob on 4/7/17.
 */

public class Fixture implements Parcelable{

    String Date;
    String Status;
    String HomeTeam;
    String AwayTeam;
   // String TimeStart;
    String HomeGoals;
    String AwayGoals;
    String Competition;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getHomeTeam() {
        return HomeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        HomeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return AwayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        AwayTeam = awayTeam;
    }

//    public String getTimeStart() {
//        return TimeStart;
//    }
//
//    public void setTimeStart(String timeStart) {
//        TimeStart = timeStart;
//    }

    public String getHomeGoals() {
        return HomeGoals;
    }

    public void setHomeGoals(String homeGoals) {
        HomeGoals = homeGoals;
    }

    public String getAwayGoals() {
        return AwayGoals;
    }

    public void setAwayGoals(String awayGoals) {
        AwayGoals = awayGoals;
    }

    public String getCompetition() {
        return Competition;
    }

    public void setCompetition(String competition) {
        Competition = competition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(Date);
        out.writeString(Status);
        out.writeString(HomeTeam);
        out.writeString(AwayTeam);
      //  out.writeString(TimeStart);
        out.writeString(HomeGoals);
        out.writeString(AwayGoals);
        out.writeString(Competition);
    }
    public Fixture(String date, String status, String homeTeam, String awayTeam, String homeGoals, String awayGoals, String competition){
        Date = date;
        Status = status;
        HomeTeam = homeTeam;
        AwayTeam = awayTeam;
        //TimeStart = timeStart;
        HomeGoals = homeGoals;
        AwayGoals = awayGoals;
        Competition = competition;
    }
    private Fixture(Parcel in){
        Date = in.readString();
        Status = in.readString();
        HomeTeam = in.readString();
        AwayTeam = in.readString();
       // TimeStart = in.readString();
        HomeGoals = in.readString();
        AwayGoals = in.readString();
        Competition = in.readString();
    }

    public static final Parcelable.Creator<Fixture> CREATOR = new Parcelable.Creator<Fixture>(){

        @Override
        public Fixture createFromParcel(Parcel parcel) {
            return new Fixture(parcel);
        }

        @Override
        public Fixture[] newArray(int i) {
            return new Fixture[i];
        }
    };
}
