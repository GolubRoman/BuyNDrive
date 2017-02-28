package com.golubroman.golub.warehouse.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 23.02.2017.
 */
//новая/бу -> тип кузова -> цвет -> марка
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }
    private ContentValues cv;
    final static String tableCAR = "tableCAR";
    final static String tablePRICE = "tablePRICE";
    final static String columnCONDITION = "columnCONDITION";
    final static String columnSALOON = "columnSALOON";
    final static String columnCOLOR = "columnCOLOR";
    final static String columnCAR = "columnCAR";
    final static String columnPRICE = "columnPRICE";
    final static String column_ID = "_ID";

    @Override
    public void onCreate(SQLiteDatabase db) {
        cv = new ContentValues();
        db.execSQL("CREATE TABLE " + tableCAR + " ("
            + column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + columnCONDITION + " TEXT NOT NULL, "
                + columnSALOON + " TEXT NOT NULL, "
                + columnCOLOR + " TEXT NOT NULL, "
                + columnCAR + " TEXT NOT NULL"
                    + ");");
        for(int i = 0; i < DBQueries.getCONDITION().length; i++){
            cv.clear();
            cv.put(columnCONDITION, DBQueries.getCONDITION()[i]);
            cv.put(columnSALOON, DBQueries.getSALOON()[i]);
            cv.put(columnCOLOR, DBQueries.getCOLOR()[i]);
            cv.put(columnCAR, DBQueries.getCAR()[i]);
            db.insert(tableCAR, null, cv);
        }
        db.execSQL("CREATE TABLE " + tablePRICE + " ( "
            + column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + columnPRICE + " INTEGER NOT NULL "
                    + ");");
        for(int i = 0; i < DBQueries.getPRICE().length; i++){
            cv.clear();
            cv.put(columnPRICE, DBQueries.getPRICE()[i]);
            db.insert(tablePRICE, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableCAR);
        db.execSQL("DROP TABLE IF EXISTS " + tablePRICE);
        onCreate(db);
    }
}
