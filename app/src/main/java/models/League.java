package models;

/**
 * Created by rob on 4/10/17.
 */

public class League {
    String LeagueName;
    int LeagueLogo;

    public League(String leagueName, int leagueLogo){
        this.LeagueName = leagueName;
        this.LeagueLogo = leagueLogo;
    };

    public String getLeagueName() {
        return LeagueName;
    }

    public void setLeagueName(String leagueName) {
        LeagueName = leagueName;
    }

    public int getLeagueLogo() {
        return LeagueLogo;
    }

    public void setLeagueLogo(int leagueLogo) {
        LeagueLogo = leagueLogo;
    }

}
