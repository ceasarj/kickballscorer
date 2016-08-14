package ceasar.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.NumberPicker;
import android.widget.TextView;

public class ScoreKeepingAcivity extends AppCompatActivity
        implements View.OnClickListener{

    private Chronometer cMeter;
    private TextView outs;
    private TextView innings;
    private TextView score;
    private Button outsButton;
    private Button scoreButton;

    private KickBallGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_keeping);

        createNewGame("Home Team",10, "Away Team", 10);

        cMeter = (Chronometer) findViewById(R.id.timer);
        cMeter.start();
        cMeter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                Log.d("Time", cMeter.getText().toString());
            }
        });

        // find all the views by id
        findViewsById();
        outsButton.setClickable(true);
        scoreButton.setClickable(true);
    }

    private void createNewGame(String homeTeamName, int numOfHomePlayers,
                               String awayTeamName, int numOfAwayPlayers){
        Team homeTeam = new Team(homeTeamName);
        Team awayTeam = new Team(awayTeamName);
        homeTeam.generatePlayers(numOfHomePlayers);
        awayTeam.generatePlayers(numOfAwayPlayers);
        game = new KickBallGame(homeTeam, awayTeam, 4);
    }

    private void findViewsById(){
        outs = (TextView) findViewById(R.id.outs);
        innings = (TextView) findViewById(R.id.innings);
        score = (TextView) findViewById(R.id.score);
        outsButton = (Button) findViewById(R.id.outs_button);
        scoreButton = (Button) findViewById(R.id.score_button);
    }

    @Override
    public void onClick(View view) {
        if(view == outsButton){
            game.addOut();
            outs.setText(game.getOuts());
            innings.setText(game.getInning());
        }
        else if(view == scoreButton){
            game.addPoint();
            score.setText(game.getHomeTeam().getScore() + " : " +game.getAwayTeam().getScore());
        }
    }
}
