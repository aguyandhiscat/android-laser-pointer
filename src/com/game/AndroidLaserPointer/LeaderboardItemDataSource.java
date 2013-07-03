package com.game.AndroidLaserPointer;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LeaderboardItemDataSource {

    // Database fields
    private SQLiteDatabase database;
    private LeaderboardSqliteHelper dbHelper;
    private String[] allColumns = { LeaderboardSqliteHelper.C_ID,
            LeaderboardSqliteHelper.C_NAME, LeaderboardSqliteHelper.C_SCORE };

    public LeaderboardItemDataSource(Context context) {
        dbHelper = new LeaderboardSqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public LeaderboardItem create(String name, int score) {
        ContentValues values = new ContentValues();
        values.put(LeaderboardSqliteHelper.C_NAME, name);
        values.put(LeaderboardSqliteHelper.C_SCORE, score);
        long insertId = database.insert(LeaderboardSqliteHelper.TABLE, null, values);
        Cursor cursor = database.query(LeaderboardSqliteHelper.TABLE,
                allColumns, LeaderboardSqliteHelper.C_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        LeaderboardItem newItem = toObject(cursor);
        cursor.close();
        return newItem;
    }

    public List<LeaderboardItem> getAll() {
        List<LeaderboardItem> list = new ArrayList<LeaderboardItem>();

        Cursor cursor = database.query(LeaderboardSqliteHelper.TABLE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LeaderboardItem comment = toObject(cursor);
            list.add(comment);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return list;
    }

    public List<LeaderboardItem> getTop(int num) {
        List<LeaderboardItem> list = new ArrayList<LeaderboardItem>();

        Cursor cursor = database.query(false, LeaderboardSqliteHelper.TABLE,
                allColumns, null, null, null, null, LeaderboardSqliteHelper.C_SCORE + " DESC", ""+num);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LeaderboardItem comment = toObject(cursor);
            list.add(comment);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return list;
    }

    private LeaderboardItem toObject(Cursor cursor) {
        LeaderboardItem item = new LeaderboardItem();
        item.setId(cursor.getLong(0));
        item.setName(cursor.getString(1));
        item.setScore(cursor.getInt(2));
        return item;
    }
}
