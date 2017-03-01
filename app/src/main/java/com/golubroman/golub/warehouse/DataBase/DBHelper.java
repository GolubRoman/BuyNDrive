package com.golubroman.golub.warehouse.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Golub Roman on 23.02.2017.
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
        /*  Query for creating table Car with columns:
                Condition, Saloon, Color, Car, _ID
            */

        db.execSQL("CREATE TABLE " + tableCAR + " ("
            + column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + columnCONDITION + " TEXT NOT NULL, "
                + columnSALOON + " TEXT NOT NULL, "
                + columnCOLOR + " TEXT NOT NULL, "
                + columnCAR + " TEXT NOT NULL"
                    + ");");

        /*  Filling the table Car with columns:
                Condition, Saloon, Color, Car, _ID
            */
        for(int i = 0; i < DBQueries.CONDITION.length; i++){
            cv.clear();
            cv.put(columnCONDITION, DBQueries.CONDITION[i]);
            cv.put(columnSALOON, DBQueries.SALOON[i]);
            cv.put(columnCOLOR, DBQueries.COLOR[i]);
            cv.put(columnCAR, DBQueries.CAR[i]);
            db.insert(tableCAR, null, cv);
        }

         /*  Query for creating table Price with columns:
                Price, _ID
            */

        db.execSQL("CREATE TABLE " + tablePRICE + " ( "
            + column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + columnPRICE + " INTEGER NOT NULL "
                    + ");");

         /*  Filling the table Price with columns:
                Price, _ID
            */

        for(int i = 0; i < DBQueries.PRICE.length; i++){
            cv.clear();
            cv.put(columnPRICE, DBQueries.PRICE[i]);
            db.insert(tablePRICE, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*  Executing SQL query of deleting tables
                Car and Price if they exist
            */
        db.execSQL("DROP TABLE IF EXISTS " + tableCAR);
        db.execSQL("DROP TABLE IF EXISTS " + tablePRICE);
        /*  Creating new tables Car and Price
            */
        onCreate(db);
    }
}
