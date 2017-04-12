package models;

/**
 * Created by rob on 4/12/17.
 */

public class TableClub {
    String Rank;
    String Team;
    String PlayedGames;
    String Points;
    String Goals;
    String GoalsAgainst;
    String GoalDifference;
    String Wins;
    String Draws;
    String Losses;

    public TableClub ( String Rank, String Team, String PlayedGames, String Points, String Goals, String GoalsAgainst, String GoalDifference, String Wins, String Draws, String Losses){
        this.Rank = Rank;
        this.Team = Team;
        this.PlayedGames = PlayedGames;
        this.Points = Points;
        this.Goals = Goals;
        this.GoalsAgainst = GoalsAgainst;
        this.GoalDifference = GoalDifference;
        this.Wins = Wins;
        this.Draws = Draws;
        this.Losses = Losses;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String team) {
        Team = team;
    }


    public String getPlayedGames() {
        return PlayedGames;
    }

    public void setPlayedGames(String playedGames) {
        PlayedGames = playedGames;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    public String getGoals() {
        return Goals;
    }

    public void setGoals(String goals) {
        Goals = goals;
    }

    public String getGoalsAgainst() {
        return GoalsAgainst;
    }

    public void setGoalsAgainst(String goalsAgainst) {
        GoalsAgainst = goalsAgainst;
    }

    public String getGoalDifference() {
        return GoalDifference;
    }

    public void setGoalDifference(String goalDifference) {
        GoalDifference = goalDifference;
    }

    public String getWins() {
        return Wins;
    }

    public void setWins(String wins) {
        Wins = wins;
    }

    public String getDraws() {
        return Draws;
    }

    public void setDraws(String draws) {
        Draws = draws;
    }

    public String getLosses() {
        return Losses;
    }

    public void setLosses(String losses) {
        Losses = losses;
    }
}
