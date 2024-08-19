package com.blockerplus.blockerplus.databaseHandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.blockerplus.blockerplus.Model.AppModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppsDataBase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "appsListDatabase";
    private static final String APPS_TABLE = "apps";
    private static final String ID = "id";
    private static final String PACKAGENAME = "packageaname";
    private static final String APPNAME = "appname";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + APPS_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PACKAGENAME + " TEXT, " + APPNAME + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public AppsDataBase(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + APPS_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertApp(AppModel app){
        ContentValues cv = new ContentValues();
        cv.put(PACKAGENAME, app.getPackageName());
        cv.put(STATUS, app.getStatus());
        cv.put(APPNAME,app.getAppName());
        db.insert(APPS_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public HashMap<String, AppModel> getAllApps(){

        HashMap<String, AppModel> appMap = new HashMap<>();

        Cursor cur = null;
        db.beginTransaction();

        try {
            cur = db.query(APPS_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        AppModel task = new AppModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setPackageName(cur.getString(cur.getColumnIndex(PACKAGENAME)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        task.setAppName(cur.getString(cur.getColumnIndex(APPNAME)));

                        // Use package name as key
                        String key = task.getPackageName();
                        appMap.put(key, task);
                    }while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }

        return appMap;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(APPS_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String packageName) {
        ContentValues cv = new ContentValues();
        cv.put(PACKAGENAME, packageName);
        db.update(APPS_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(APPS_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<AppModel> searchByPackageName(String packageName) {
        List<AppModel> matchingApps = new ArrayList<>();
        Cursor cursor = null;
        try {
            String selection = PACKAGENAME + "=?";
            String[] selectionArgs = { packageName };

            cursor = db.query(APPS_TABLE, null, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    AppModel app = new AppModel();
                    app.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                    app.setPackageName(cursor.getString(cursor.getColumnIndex(PACKAGENAME)));
                    app.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                    matchingApps.add(app);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return matchingApps;
    }
}