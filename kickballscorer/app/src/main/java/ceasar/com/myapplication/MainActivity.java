package ceasar.com.myapplication;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Sending out a log", "Log test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        Resources res = getResources();
        String[] titles = res.getStringArray(R.array.menu_titles);
        TypedArray icons = res.obtainTypedArray(R.array.menu_icons);

        ArrayList<ContentModel> contentModels = new ArrayList<>();

        // populate the content
        for (int i = 0; i<titles.length; i++) {
            contentModels.add(new ContentModel(titles[i], icons.getDrawable(i)));
            Log.d("title", titles[i]);
            Log.d("drawable", icons.getDrawable(i).toString());
        }

        RecyclerView rView = (RecyclerView) findViewById(R.id.item_list);
        rView.setAdapter(new MainViewAdapter(contentModels, this));
        layoutManager = new LinearLayoutManager(this);
        rView.setLayoutManager(layoutManager);
    }
}
