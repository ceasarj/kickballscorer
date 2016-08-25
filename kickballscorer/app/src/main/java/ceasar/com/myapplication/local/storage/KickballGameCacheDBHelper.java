package ceasar.com.myapplication.local.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Store game names in db just in case if game name was not properly removed from firebase db
 * **/
public class KickballGameCacheDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_CACHE_TABLE =
            "CREATE TABLE " + KickballGameCacheContract.TABLE_NAME + " ("
            + KickballGameCacheContract._ID + " INTEGER PRIMARY KEY,"
            + KickballGameCacheContract.GAME_NAME + "TEXT"
            +")";

    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + KickballGameCacheContract.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "kickball_game.db";

    public KickballGameCacheDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CACHE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addGameName(String gameName){
        ContentValues values = new ContentValues();
        values.put("game_name", gameName);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(KickballGameCacheContract.TABLE_NAME, null, values);
        db.close();
    }

    public List<String> getGameNames(){
        List<String> names = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(KickballGameContract.TABLE_NAME,
                new String[] {KickballGameCacheContract.GAME_NAME}, null, null, null, null, null);
        if(c != null){
            while(c.moveToNext()){
                names.add(c.getString(0));
            }
        }

        return names;
    }
}
