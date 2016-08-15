package ceasar.com.myapplication;

/**
 * Created by ceejay562 on 8/12/2016.
 */

public class KickBallGame {

    private int outs;
    private int strikes;
    private int inning;
    private int maxStrikes;

    private Team homeTeam;
    private Team awayTeam;
    private Team currentlyKickingTeam;

    public KickBallGame(Team homeTeam, Team awayTeam, int maxStrikes){
        outs = 0;
        strikes = 0;
        inning = 1;

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.maxStrikes = maxStrikes;
        // away team kicks first
        currentlyKickingTeam = awayTeam;
    }

    public void addPoint(){
        currentlyKickingTeam.addPoint();
    }

    public void addOut(){
        if(outs < 2) {
            outs++;
        }
        else{
            outs = 0;
            inning++;
            if(currentlyKickingTeam == awayTeam){
                currentlyKickingTeam = homeTeam;
            }
            else{
                currentlyKickingTeam = awayTeam;
            }
        }
        currentlyKickingTeam.getCurrentKicker().out();
    }

    public void hit(){
        currentlyKickingTeam.getCurrentKicker().hit();
    }

    public void subOut(){
        if(outs > 0)
            outs--;
    }

    public void addStrike(){
        // player striked out
        if(strikes == (maxStrikes-1)){
            strikes = 0;
            addOut();
        }
        else{
            strikes++;
        }
    }

    public void subStrike(){
        if(strikes > 0)
            strikes--;
    }

    public int getOuts(){
        return outs;
    }

    public int getInning(){
        return inning;
    }

    public Team getHomeTeam(){
        return homeTeam;
    }

    public Team getAwayTeam(){
        return awayTeam;
    }
}
