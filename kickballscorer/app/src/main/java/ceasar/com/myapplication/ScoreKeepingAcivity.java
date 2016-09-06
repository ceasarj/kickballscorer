package ceasar.com.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ceasar.com.myapplication.local.storage.KickballGameDBHelper;

public class ScoreKeepingAcivity extends AppCompatActivity
        implements View.OnClickListener {
    // key names
    private static final String HOME_NAME = "home_team_name";
    private static final String HOME_SCORE = "home_team_score";
    private static final String AWAY_NAME = "away_team_name";
    private static final String AWAY_SCORE = "away_team_score";
    private static final String INNING = "innigs";
    private static final String OUTS = "outs";
    private static final String STRIKES = "strikes";
    private static final String START_TIME = "start_time";

    private Chronometer cMeter;
    private ImageButton outPlus;
    private ImageButton outMinus;
    private ImageButton scorePlus;
    private ImageButton scoreMinus;
    private TextView outs;
    private TextView innings;
    private TextView homeTeamScore;
    private TextView awayTeamScore;
    private Button saveButton;

    private KickBallGame game;
    private KickballGameDBHelper dbHelper;

    private String gameName;
    private String randomKey;
    private boolean isBroadcasting;

    // references
    private DatabaseReference refRoot = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseReference gameNameRoot;
    private DatabaseReference gameNameChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_keeping);
        reqGameNameFromUser();
        findViewsById();
        createNewGame("Home Team", 10, "Away Team", 10);

        outPlus.setOnClickListener(this);
        outMinus.setOnClickListener(this);
        scorePlus.setOnClickListener(this);
        scoreMinus.setOnClickListener(this);

        dbHelper = new KickballGameDBHelper(getBaseContext());

        refRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                finish();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void createNewGame(String homeTeamName, int numOfHomePlayers,
                               String awayTeamName, int numOfAwayPlayers) {
        Team homeTeam = new Team(homeTeamName);
        Team awayTeam = new Team(awayTeamName);
        homeTeam.generatePlayers(numOfHomePlayers);
        awayTeam.generatePlayers(numOfAwayPlayers);
        game = new KickBallGame(homeTeam, awayTeam, 4);
    }

    @Override
    public void onClick(View view) {
        if (view == outPlus) {
            game.addOut();
            outs.setText(game.getOuts() + " outs");
            innings.setText(game.getInning() + "");
            setValuesOfChildren();
        } else if (view == scorePlus) {
            game.addPoint();
            awayTeamScore.setText(String.valueOf(game.getAwayTeam().getScore()));
            homeTeamScore.setText(String.valueOf(game.getHomeTeam().getScore()));
            setValuesOfChildren();
        } else if (view == saveButton) {
            GameModel gm = new GameModel();
            gm.awayTeamScore = game.getAwayTeam().getScore();
            gm.awayTeamName = game.getAwayTeam().getTeamName();
            gm.homeTeamScore = game.getHomeTeam().getScore();
            gm.homeTeamName = game.getHomeTeam().getTeamName();
            dbHelper.addGame(gm);
            gameNameRoot.child(randomKey).removeValue();            // remove random key
            //gameNameRoot.removeValue();
            //finish();
            /**
             * TODO: Delete the list and for loop before deployment
             * **/
            List<GameModel> games = dbHelper.getGames();
            for (int i = 0; i < games.size(); i++) {
                Log.d("Home team name", games.get(i).homeTeamName);
                Log.d("Home score", games.get(i).homeTeamScore + "");
                Log.d("Away team name", games.get(i).awayTeamName);
                Log.d("Away team score", games.get(i).awayTeamScore + "");
            }
        }
    }

    private void findViewsById() {
        outs = (TextView) findViewById(R.id.outs_tv);
        outPlus = (ImageButton) findViewById(R.id.add_out_button);
        outMinus = (ImageButton) findViewById(R.id.minus_out_button);
        scorePlus = (ImageButton) findViewById(R.id.plus_score_button);
        scoreMinus = (ImageButton) findViewById(R.id.minus_score_button);
        awayTeamScore = (TextView) findViewById(R.id.away_team_score);
        homeTeamScore = (TextView) findViewById(R.id.home_team_score);
        innings = (TextView) findViewById(R.id.inning_number);
        saveButton = (Button) findViewById(R.id.save_button);
        cMeter = (Chronometer) findViewById(R.id.timer);

    }

    private void reqGameNameFromUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name of private room: ");

        // put Edit Text inside of the alert dialog
        final EditText gameInput = new EditText(this);
        builder.setView(gameInput);

        builder.setPositiveButton("Broadcast", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gameName = gameInput.getText().toString();
                HashMap<String, Object> map = new HashMap<>();
                map.put(gameName, "");
                refRoot.updateChildren(map);
                generateRandomKey();
                createChildren();
                isBroadcasting = true;
                cMeter.start();
            }
        });

        // cancelling means the user does not want to send live data
        builder.setNegativeButton("offline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                isBroadcasting = false;
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    /**
     * Generate random key and store it in firebase
     **/
    private void generateRandomKey() {
        // point at the game name in fire db
        gameNameRoot = FirebaseDatabase.getInstance()
                .getReference().child(gameName);
        randomKey = gameNameRoot.push().getKey();
        Map<String, Object> map = new HashMap<>();
        map.put(randomKey, "");
        gameNameRoot.updateChildren(map);
        gameNameChild = gameNameRoot.child(randomKey);
    }

    private void createChildren() {
        Map<String, Object> map = new HashMap<>();
        map.put(HOME_NAME, game.getHomeTeam().getTeamName());
        map.put(HOME_SCORE, game.getHomeTeam().getScore());
        map.put(AWAY_NAME, game.getAwayTeam().getTeamName());
        map.put(AWAY_SCORE, game.getAwayTeam().getScore());
        map.put(INNING, game.getInning());
        map.put(OUTS, game.getOuts());
        map.put(STRIKES, game.getStrikes());
        long startTime = System.currentTimeMillis()/1000;
        map.put(START_TIME, startTime);
        gameNameChild.updateChildren(map);
    }

    private void setValuesOfChildren() {
        if(isBroadcasting) {
            gameNameChild.child(HOME_SCORE).setValue(game.getHomeTeam().getScore());
            gameNameChild.child(AWAY_SCORE).setValue(game.getAwayTeam().getScore());
            gameNameChild.child(INNING).setValue(game.getInning());
            gameNameChild.child(OUTS).setValue(game.getOuts());
            gameNameChild.child(STRIKES).setValue(game.getStrikes());
        }
    }
}
