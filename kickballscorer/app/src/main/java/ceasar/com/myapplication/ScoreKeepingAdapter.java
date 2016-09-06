package ceasar.com.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by ceejay562 on 8/31/2016.
 */

public class ScoreKeepingAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scorekeeping_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //TextView
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageButton sub;
        ImageButton add;
        TextView desc;

        public ViewHolder(View v){
            super(v);
            sub = (ImageButton) v.findViewById(R.id.sub_button);
            add = (ImageButton) v.findViewById(R.id.add_button);
            desc = (TextView) v.findViewById(R.id.number);
        }


        @Override
        public void onClick(View view) {

        }
    }
}
