package com.devkallouch.german;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ads.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "ad_time";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TIME = "lastAdTime";

    public AdDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIME + " INTEGER DEFAULT 0)";
        db.execSQL(CREATE_TABLE);

        // إدراج قيمة ابتدائية
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, 0);
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // حفظ وقت الإعلان
    public void saveAdTime(long time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, time);

        // إدراج أو تحديث السجل الوحيد في الجدول
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    // استرجاع وقت آخر إعلان
    public long getLastAdTime() {
        SQLiteDatabase db = this.getReadableDatabase();
        long lastAdTime = 0;

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TIME + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            lastAdTime = cursor.getLong(0);
        }

        cursor.close();
        db.close();
        return lastAdTime;
    }
}
