package com.game.AndroidLaserPointer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LeaderboardSqliteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "laserpointer.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE = "leaderboard";
    public static final String C_ID     = "_id";
    public static final String C_NAME   = "name";
    public static final String C_SCORE  = "score";

    public LeaderboardSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_NAME + " TEXT NOT NULL, "
            + C_SCORE + " INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LeaderboardSqliteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
