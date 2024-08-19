package com.blockerplus.blockerplus.databaseHandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blockerplus.blockerplus.Model.ItemModel;
import com.blockerplus.blockerplus.Model.UserModal;

import java.util.ArrayList;
import java.util.List;

public class UserDataBase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "UserDatabase";
    private static final String USER_TABLE = "user";
    private static final String ID = "id";
    private static final String EMAIL = "item";
    private static final String USERNAME = "name";
    private static final String PHOTO = "photo";
    private static final String PARTNEREMAIL = "partneremail";
    private static final String PIN = "pin";
    private static final String ISPREMIUMUSER = "isPremiumUser";
    private static final String SENDPINAFTER = "sendPinAfter";
    private static final String PRODUCTID = "productId";
    private static final String PRODUCTTOKEN = "productToken";

    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + USER_TABLE + "(" +
            ID + " TEXT, " +
            EMAIL + " TEXT, " +
            PHOTO + " TEXT, " +
            USERNAME + " TEXT, " +
            PARTNEREMAIL + " TEXT, " +
            ISPREMIUMUSER + " TEXT, " +
            PIN + " TEXT, " +
            SENDPINAFTER + " TEXT, " +
            PRODUCTID + " TEXT, " +
            PRODUCTTOKEN + " TEXT)";

    private SQLiteDatabase db;

    public UserDataBase(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertItem(UserModal user){
        ContentValues cv = new ContentValues();
        cv.put(EMAIL, user.getEmail());
        cv.put(ID, user.getId());
        cv.put(USERNAME,user.getUserName());
        cv.put(PHOTO,user.getPhoto());
        cv.put(PARTNEREMAIL,user.getPartnerEmail());
        cv.put(PIN,user.getPin());
        cv.put(ISPREMIUMUSER,user.getIsPremiumUser());
        cv.put(PRODUCTID,user.getProductId());
        cv.put(PRODUCTTOKEN,user.getProductToken());
        db.insert(USER_TABLE, null, cv);
    }

    public void setIsPremiumUser(String id,String isPremiumUser) {
        ContentValues cv = new ContentValues();
        cv.put(ISPREMIUMUSER, isPremiumUser);
        String whereClause = ID + "=?";
        String[] whereArgs = {id};
        db.update(USER_TABLE, cv, whereClause, whereArgs);
    }

    public void addPartnerEmail(String id, String partnerEmail) {
        ContentValues cv = new ContentValues();
        cv.put(PARTNEREMAIL, partnerEmail);
        String whereClause = ID + "=?";
        String[] whereArgs = {id};
        db.update(USER_TABLE, cv, whereClause, whereArgs);
    }

    public void addPin(String id, String pin) {
        ContentValues cv = new ContentValues();
        cv.put(PIN, pin);

        String whereClause = ID + "=?";
        String[] whereArgs = {id};

        db.update(USER_TABLE, cv, whereClause, whereArgs);
    }

    public void removeUser(String id) {
        String whereClause = ID + "=?";
        String[] whereArgs = {id};

        db.delete(USER_TABLE, whereClause, whereArgs);
    }

    @SuppressLint("Range")
    public List<UserModal> getAllItems(){
        List<UserModal> itemList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(USER_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        UserModal user = new UserModal();
                        user.setId(cur.getString(cur.getColumnIndex(ID)));
                        user.setEmail(cur.getString(cur.getColumnIndex(EMAIL)));
                        user.setUserName(cur.getString(cur.getColumnIndex(USERNAME)));
                        user.setPhoto(cur.getString(cur.getColumnIndex(PHOTO)));
                        user.setPartnerEmail(cur.getString(cur.getColumnIndex(PARTNEREMAIL)));
                        user.setPin(cur.getString(cur.getColumnIndex(PIN)));
                        user.setIsPremiumUser(cur.getString(cur.getColumnIndex(ISPREMIUMUSER)));
                        user.setProductId(cur.getString(cur.getColumnIndex(PRODUCTID)));
                        user.setProductToken(cur.getString(cur.getColumnIndex(PRODUCTTOKEN)));
                        itemList.add(user);
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
