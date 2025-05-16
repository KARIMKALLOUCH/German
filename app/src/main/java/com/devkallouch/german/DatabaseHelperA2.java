package com.devkallouch.german;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperA2 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cardsss.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "card_status";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IS_READ = "isRead1";

    public DatabaseHelperA2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_IS_READ + " INTEGER DEFAULT 0)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // تحديث أو إضافة حالة الكارت
    public void updateCardStatus(int cardId, boolean isRead) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, cardId);
        values.put(COLUMN_IS_READ, isRead ? 1 : 0);

        long result = db.replace(TABLE_NAME, null, values); // استبدال إذا كان موجودًا
        db.close();
    }

    // استرجاع حالة الكارت
    public boolean getCardStatus(int cardId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME, new String[]{COLUMN_IS_READ},
                    COLUMN_ID + "=?", new String[]{String.valueOf(cardId)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_READ)) == 1;
            }
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return false;
    }

}
