package ceasar.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

import ceasar.com.myapplication.local.storage.KickballGameDBHelper;

public class ScoreKeepingAcivity extends AppCompatActivity
        implements View.OnClickListener{

    private Chronometer cMeter;
    private TextView outs;
    private TextView innings;
    private TextView score;
    private Button outsButton;
    private Button scoreButton;
    private Button saveButton;

    private KickBallGame game;
    private KickballGameDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_keeping);

        createNewGame("Home Team",10, "Away Team", 10);

        cMeter = (Chronometer) findViewById(R.id.timer);
//        cMeter.start();
//        cMeter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer chronometer) {
//                Log.d("Time", cMeter.getText().toString());
//            }
//        });

        // find all the views by id
        findViewsById();
        outsButton.setOnClickListener(this);
        scoreButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        dbHelper = new KickballGameDBHelper(getBaseContext());

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
        saveButton = (Button) findViewById(R.id.save);
    }

    @Override
    public void onClick(View view) {
        if(view == outsButton){
            game.addOut();
            outs.setText(game.getOuts() + "");
            innings.setText(game.getInning() + "");
        }
        else if(view == scoreButton){
            game.addPoint();
            score.setText(game.getHomeTeam().getScore() + " : " +game.getAwayTeam().getScore());
        }
        else if(view == saveButton){
            GameModel gm = new GameModel();
            gm.awayTeamScore = game.getAwayTeam().getScore();
            gm.awayTeamName = game.getAwayTeam().getTeamName();
            gm.homeTeamScore = game.getHomeTeam().getScore();
            gm.homeTeamName = game.getHomeTeam().getTeamName();
            dbHelper.addGame(gm);
            List<GameModel> games = dbHelper.getGames();
            for(int i=0; i<games.size(); i++) {
                Log.d("Home team name", games.get(i).homeTeamName);
                Log.d("Home score", games.get(i).homeTeamScore + "");
                Log.d("Away team name", games.get(i).awayTeamName);
                Log.d("Away team score", games.get(i).awayTeamScore + "");
            }
        }
    }
}
