package ceasar.com.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by ceejay562 on 8/17/2016.
 */

public class KickballScorerLiveActivity extends AppCompatActivity {

    private TextView innings;
    private TextView outs;
    private TextView homeScore;
    private TextView homeName;
    private TextView awayScore;
    private TextView awayName;
    private Chronometer timer;
    private DatabaseReference gameNameRef;
    private DatabaseReference root;
    private String gameName;
    private Queue<String> data;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.scorer_live_activity);
        root = FirebaseDatabase.getInstance().getReference().getRoot();
        Log.d("OnCreate", "layout is inflating");
        findViews();
        data = new LinkedList<>();
        reqGameName();
    }

    private void findViews(){
        outs = (TextView) findViewById(R.id.outs_number_live);
        innings = (TextView) findViewById(R.id.inning_number_live);
        homeScore = (TextView) findViewById(R.id.home_team_score_live);
        homeName = (TextView) findViewById(R.id.home_team_name_live);
        awayScore = (TextView) findViewById(R.id.away_team_score_live);
        awayName = (TextView) findViewById(R.id.away_team_name_live);
        timer = (Chronometer) findViewById(R.id.timer_live);
    }

    private void reqGameName(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the game you wish get scoring for");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                gameName = input.getText().toString();
                gameNameRef = FirebaseDatabase.getInstance().getReference().child(gameName);

                gameNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            // right here is to test if the child has a key.
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                gameNameRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        // add data to queue
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            data.add(((DataSnapshot) child).getValue().toString());
                        }

                        awayName.setText(data.poll());
                        awayScore.setText(data.poll());
                        homeName.setText(data.poll());
                        homeScore.setText(data.poll());
                        innings.setText(data.poll());
                        outs.setText(data.poll());
                        data.poll(); // time
                        data.poll(); // strikes
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Iterator it = dataSnapshot.getChildren().iterator();

                        while(it.hasNext()){
                            String child = ((DataSnapshot) it.next()).getValue().toString();
                            data.add(child);
                        }

                        awayName.setText(data.poll());
                        awayScore.setText(data.poll());
                        homeName.setText(data.poll());
                        homeScore.setText(data.poll());
                        innings.setText(data.poll());
                        outs.setText(data.poll());
                        data.poll(); // time
                        data.poll(); // strikes
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                root.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        // check if the child removed is the one we are listening to
                        if(gameName.equals(dataSnapshot.getKey())){
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    KickballScorerLiveActivity.this);
                            builder.setTitle("Referee has ended the game");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            // end the activity if the user tries to hit back button
                            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    finish();
                                }
                            });
                            builder.show();
                        }
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                finish();
            }
        });
        builder.show();
    }
}
