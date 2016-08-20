package ceasar.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import ceasar.com.myapplication.local.storage.KickballGameDBHelper;

public class GameHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_history_activity);
        setUpRecycler();
    }

    private void setUpRecycler(){
        KickballGameDBHelper dbHelper = new KickballGameDBHelper(getBaseContext());
        List<GameModel> games = dbHelper.getGames();
        Log.d("Size of list", String.valueOf(games.size()));
        RecyclerView rView = (RecyclerView) findViewById(R.id.history_item_list);
        rView.setAdapter(new SavedGamesAdapter(games));
        rView.setLayoutManager(new LinearLayoutManager(this));
    }
}
