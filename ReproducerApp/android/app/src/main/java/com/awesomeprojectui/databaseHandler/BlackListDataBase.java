package com.blockerplus.blockerplus.databaseHandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blockerplus.blockerplus.Model.AppModel;
import com.blockerplus.blockerplus.Model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class BlackListDataBase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "BlackListDatabase";
    private static final String BLACKLIST_TABLE = "blacklist";
    private static final String ID = "id";
    private static final String ITEM = "item";
    private static final String ISAPP = "isapp";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + BLACKLIST_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM + " TEXT, "+ ISAPP + " INTEGER, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public BlackListDataBase(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + BLACKLIST_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertItem(ItemModel item){
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item.getItem());
        cv.put(STATUS, item.getStatus());
        cv.put(ISAPP,item.getIsApp());
        db.insert(BLACKLIST_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public List<ItemModel> getAllItems(){
        List<ItemModel> itemList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(BLACKLIST_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ItemModel item = new ItemModel();
                        item.setId(cur.getInt(cur.getColumnIndex(ID)));
                        item.setItem(cur.getString(cur.getColumnIndex(ITEM)));
                        item.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        item.setIsApp(cur.getInt(cur.getColumnIndex(ISAPP)));
                        itemList.add(item);
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

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(BLACKLIST_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String item) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item);
        db.update(BLACKLIST_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(BLACKLIST_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<ItemModel> searchByItem(String item) {
        List<ItemModel> matchingItems = new ArrayList<>();
        Cursor cursor = null;
        try {
            String selection = ITEM + "=?";
            String[] selectionArgs = { item };

            cursor = db.query(BLACKLIST_TABLE, null, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ItemModel item1 = new ItemModel();
                    item1.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                    item1.setItem(cursor.getString(cursor.getColumnIndex(ITEM)));
                    item1.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                    item1.setIsApp(cursor.getInt(cursor.getColumnIndex(ISAPP)));
                    matchingItems.add(item1);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return matchingItems;
    }
}
