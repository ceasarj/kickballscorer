package ceasar.com.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ceejay562 on 8/18/2016.
 */

public class SavedGamesAdapter extends RecyclerView.Adapter<SavedGamesAdapter.ViewHolder> {

    private List<GameModel> games;

    public SavedGamesAdapter(List<GameModel> games){
        this.games = games;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_history_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GameModel gm = games.get(position);
        holder.homeTeam.setText(gm.homeTeamName + "\n" + gm.homeTeamScore);
        holder.awayTeam.setText(gm.awayTeamName + "\n" + gm.awayTeamScore);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }


    /**
     * ViewHolder
     * **/
    public class ViewHolder extends RecyclerView.ViewHolder{
        View v;
        TextView homeTeam;
        TextView awayTeam;
        public ViewHolder(View v){
            super(v);
            this.v = v;
            homeTeam = (TextView) v.findViewById(R.id.home_team_item);
            awayTeam = (TextView) v.findViewById(R.id.away_team_item);
        }
    }
}
