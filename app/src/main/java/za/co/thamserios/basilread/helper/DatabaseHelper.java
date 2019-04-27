package za.co.thamserios.basilread.helper;

import android.database.sqlite.SQLiteDatabase;

import za.co.thamserios.basilread.ContextUtil;

/**
 * Created by robson on 2017/03/07.
 */
public class DatabaseHelper {
    private static DatabaseHelper ourInstance = new DatabaseHelper();
    private BasilReadDatabaseHelper dbHelper = new BasilReadDatabaseHelper(ContextUtil.getContext());
    private SQLiteDatabase db;
    public static DatabaseHelper getInstance() {
        return ourInstance;
    }

    private DatabaseHelper() {
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 4);
        db = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDb(){
        return db;
    }
}
