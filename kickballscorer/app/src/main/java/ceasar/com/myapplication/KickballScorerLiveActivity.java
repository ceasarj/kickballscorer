package ceasar.com.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
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
import java.util.List;

/**
 * Created by ceejay562 on 8/17/2016.
 */

public class KickballScorerLiveActivity extends AppCompatActivity {

    private TextView innings;
    private TextView outs;

    private DatabaseReference gameNameRef;
    private DatabaseReference root;
    private String gameName;
    private List<String> data;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.scorer_live_activity);
        root = FirebaseDatabase.getInstance().getReference().getRoot();
        Log.d("OnCreate", "layout is inflating");
        findViews();
        data = new ArrayList<>();
        reqGameName();
    }

    private void findViews(){
        outs = (TextView) findViewById(R.id.outs_live);
        innings = (TextView) findViewById(R.id.inning_live);
    }

    private void reqGameName(){
        Log.i("Input!!!!!", "ajksndlkjasnldkjnasldkjnaslkjdnlaskjdnlkasjndlk");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the game you wish get scoring for");
        final EditText input = new EditText(this);
        builder.setView(input);
        Log.d("Input!!!!!", "ajksndlkjasnldkjnasldkjnaslkjdnlaskjdnlkasjndlk");
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
                        Iterator it = dataSnapshot.getChildren().iterator();
                        Log.d("Iterator Size",it.hasNext() + "");
                        while(it.hasNext()){
                            String child = ((DataSnapshot) it.next()).getValue().toString();
                            data.add(child);
                        }

                        outs.setText(data.get(0));
                        innings.setText(data.get(1));
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Iterator it = dataSnapshot.getChildren().iterator();

                        while(it.hasNext()){
                            String child = ((DataSnapshot) it.next()).getValue().toString();
                            data.add(child);
                        }

                        outs.setText(data.get(0));
                        outs.setText(data.get(1));
                    }

                    /** When the user ended the game. **/
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
