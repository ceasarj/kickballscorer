package ceasar.com.myapplication.local.storage;

import android.provider.BaseColumns;

/**
 * Created by ceejay562 on 8/15/2016.
 */

public final class KickballGameContract implements BaseColumns {
    public static final String TABLE_NAME = "games";
    public static final String HOME_TEAM_NAME = "home_team_name";
    public static final String AWAY_TEAM_NAME = "away_team_name";
    public static final String HOME_TEAM_SCORE = "home_team_score";
    public static final String AWAY_TEAM_SCORE = "away_team_score";
}
