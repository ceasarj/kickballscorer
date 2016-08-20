package ceasar.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ceejay562 on 8/11/2016.
 */

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.ViewHolder> {
    private final int KEEP_SCORE = 0;
    private final int WATCH_LIVE = 1;
    private final int GAME_HISTORY = 2;

    private final List<ContentModel> contentModels;
    private Activity act;

    public MainViewAdapter(List<ContentModel> contentModels, Activity mainActivity){
        this.contentModels = contentModels;
        act = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d("Binding", "Is Binding");
        holder.id.setText("  " + position);
        holder.content.setText(contentModels.get(position).getTitle());
        holder.icon.setImageDrawable(contentModels.get(position).getIcon());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(position){
                    case KEEP_SCORE:
                        Intent scoreIntent = new Intent(act, ScoreKeepingAcivity.class);
                        act.startActivity(scoreIntent);
                        break;
                    case WATCH_LIVE:
                        Intent liveIntent = new Intent(act, KickballScorerLiveActivity.class);
                        act.startActivity(liveIntent);
                        break;
                    case GAME_HISTORY:
                        Intent historyIntent = new Intent(act, GameHistoryActivity.class);
                        act.startActivity(historyIntent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentModels.size();
    }

    /**
     * ViewHolder
     * */
    public class ViewHolder extends RecyclerView.ViewHolder {
        View v;
        TextView content;
        ImageView icon;
        final TextView id;
        public ViewHolder(View view){
            super(view);
            this.v = view;
            content = (TextView) view.findViewById(R.id.content);
            icon = (ImageView) view.findViewById(R.id.icon);
            id = (TextView) view.findViewById(R.id.id);
        }
    }
}
