package ceasar.com.myapplication.local.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ceasar.com.myapplication.GameModel;


/**              CRUD operations performed here               **/
public class KickballGameDBHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_KICKBALL_GAME =
            "CREATE TABLE " + KickballGameContract.TABLE_NAME + " ("
                    + KickballGameContract._ID + " INTEGER PRIMARY KEY,"
                    + KickballGameContract.HOME_TEAM_NAME + " TEXT,"
                    + KickballGameContract.HOME_TEAM_SCORE + " INTEGER,"
                    + KickballGameContract.AWAY_TEAM_NAME + " TEXT,"
                    + KickballGameContract.AWAY_TEAM_SCORE + " INTEGER"
                    + " )";
    private static final String DROP_TABLE_KICKBALL_GAME =
            "DROP TABLE IF EXISTS " + KickballGameContract.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "kickball_game.db";

    public KickballGameDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_KICKBALL_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE_KICKBALL_GAME);
        onCreate(db);
    }

    public void addGame(GameModel game){
        // populate data for db
        ContentValues values = new ContentValues();
        values.put(KickballGameContract.HOME_TEAM_NAME, game.homeTeamName);
        values.put(KickballGameContract.HOME_TEAM_SCORE, game.homeTeamScore);
        values.put(KickballGameContract.AWAY_TEAM_NAME, game.awayTeamName);
        values.put(KickballGameContract.AWAY_TEAM_SCORE, game.awayTeamScore);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(KickballGameContract.TABLE_NAME, null, values);
        db.close();
    }

    /** get a list of all the games that have been saved in the phone **/
    public List<GameModel> getGames(){
        // name of the columns to be retreive data from
        String[] colNames = {
            KickballGameContract.HOME_TEAM_NAME,
            KickballGameContract.HOME_TEAM_SCORE,
            KickballGameContract.AWAY_TEAM_NAME,
            KickballGameContract.AWAY_TEAM_SCORE
        };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(KickballGameContract.TABLE_NAME,
                colNames, null, null, null,null, null);
        List<GameModel> games = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                GameModel gm = new GameModel();
                gm.homeTeamName = cursor.getString(0);
                gm.homeTeamScore = cursor.getInt(1);
                gm.awayTeamName = cursor.getString(2);
                gm.awayTeamScore = cursor.getInt(3);
                games.add(gm);
            }
        }
        db.close();
        return games;
    }
}
