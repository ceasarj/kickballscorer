package ceasar.com.myapplication;

/**
 * Created by ceejay562 on 8/12/2016.
 */

public class GameModel {
    private int score;
    private int outs;
    private int strikes;

    public GameModel(){
        score = 0;
        outs = 0;
        strikes = 0;
    }

    private void addPoint(){
        score++;
    }

    private void subPoint(){
        if(score > 0)
            score--;
    }

    private void addOut(){
        outs++;
    }

    private void subOut(){
        if(outs > 0)
            outs--;
    }

    private void addStrike(){
        strikes++;
    }

    private void subStrike(){
        if(strikes > 0)
            strikes--;
    }


}
