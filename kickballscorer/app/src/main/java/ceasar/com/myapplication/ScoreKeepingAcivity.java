package ceasar.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Chronometer;

public class ScoreKeepingAcivity extends AppCompatActivity {

    private Chronometer cMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_keeping);

        cMeter = (Chronometer) findViewById(R.id.timer);

        cMeter.start();

        cMeter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                Log.d("Time", cMeter.getText().toString());
            }
        });
    }
}
