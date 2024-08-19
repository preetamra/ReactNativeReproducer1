package com.blockerplus.blockerplus.databaseHandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.blockerplus.blockerplus.Model.TrackerPlusModel;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import java.util.ArrayList;
import java.util.List;

public class TrackerPlus extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "TrackerPlusDatabase";
    private static final String TRACKER_TABLE = "trackerplus";

    private static final String START_TIME = "starttime";

    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TRACKER_TABLE + "(" +
            START_TIME + " TEXT)";

    private SQLiteDatabase db;

    public TrackerPlus(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TRACKER_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertItem(TrackerPlusModel trackerPlus){
        ContentValues cv = new ContentValues();
        cv.put(START_TIME, trackerPlus.getStartTime());
        Log.v("TrackerPlus insert timer",trackerPlus.getStartTime());
        db.insert(TRACKER_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public List<TrackerPlusModel> getAllItems(){
        List<TrackerPlusModel> itemList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TRACKER_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        TrackerPlusModel trackerPlusModel = new TrackerPlusModel();
                        trackerPlusModel.setStartTime(cur.getString(cur.getColumnIndex(START_TIME)));
                        itemList.add(trackerPlusModel);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return itemList;
    }
}
