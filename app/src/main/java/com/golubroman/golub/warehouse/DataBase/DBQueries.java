package com.golubroman.golub.warehouse.DataBase;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 25.02.2017.
 */

public class DBQueries extends Fragment {
    final public static String[] CONDITION = {"Choose...", "New", "Used", "New", "New", "Used", "Used",
            "New", "Used", "New", "New", "Used", "Used",
            "New", "Used", "New", "New", "Used", "Used"};
    private static String[] SALOON = {"Choose...", "Coupe", "Sedan", "Hatchback", "Coupe",
            "Sedan", "Hatchback", "Coupe", "Sedan", "Hatchback", "Coupe", "Sedan",
            "Hatchback", "Coupe", "Sedan", "Hatchback", "Coupe", "Sedan", "Hatchback"};
    private static String[] COLOR = {"Choose...", "White", "Black", "Red",
            "Orange", "Yellow", "Green", "Blue", "Violet", "White", "Black", "Red",
            "Orange", "Yellow", "Green", "Blue", "Violet", "White", "Black"};
    private static String[] CAR = {"Choose...", "BMW X6", "BMW X1", "LandRover", "Toyota Carina",
            "Toyota Yaris", "Mercedes Benz CLS", "Mercedes Benz GLS", "Wolksvagen Passat", "Wolksvagen Golf",
            "Opel Astra", "Opel Corsa", "Volvo S60", "Volvo S50", "Citroen C4",
            "Pegeout 308", "Pegeout 408", "Pegeout CC", "Pegeout SW"};
    private static int[] PRICE = {0, 10000, 20000, 35000, 5000, 15000, 25000,
            10000, 20000, 35000, 5000, 15000, 25000,
            10000, 20000, 35000, 5000, 15000, 25000};
    public static List<String> addBuffer;
    final static String tableCAR = "tableCAR";
    final static String tablePRICE = "tablePRICE";
    final static String columnPRICE = "columnPRICE";
    final static String column_ID = "_ID";
    public static List<String> choice;
    private static List<String> columnNAMES = new ArrayList<>(
            Arrays.asList("columnCONDITION", "columnSALOON", "columnCOLOR", "columnCAR"));
    private static int cntCAR;
    private static DBHelper dbh;
    private static SQLiteDatabase db;
    private static Cursor cForSearch;
    public static List<String> stringsForSpinner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbh = new DBHelper(getContext());
        db = dbh.getWritableDatabase();
        cntCAR = CAR.length;
        choice = new ArrayList<>();


    }
    public static void getData(String currentChoice){
        choice.add(currentChoice);
        String queryString = "SELECT " + columnNAMES.get(choice.size())
                + " FROM " + tableCAR
                + " WHERE ";
        for(int i = 0; i < choice.size(); i++){
            queryString += columnNAMES.get(i) + "='"
                    + choice.get(i) + "'";
             if(i != choice.size() - 1)
                 queryString += " and ";
        }
        queryString += " OR " +
                columnNAMES.get(columnNAMES.size()-1) + "='"
                + "Choose...'";
        cForSearch = db.rawQuery(queryString, null);
        stringsForSpinner = new ArrayList<>();
        cForSearch.moveToFirst();
        cntCAR = cForSearch.getCount();
        do{
            String buffer = cForSearch.getString(cForSearch.getColumnIndex(columnNAMES.get(choice.size())));
            boolean meet = true;
            for(int i = 0; i < stringsForSpinner.size(); i++){
                if(stringsForSpinner.get(i).equals(buffer)) meet = false;
            }
            if(meet)
            stringsForSpinner.add(buffer);
        }while(cForSearch.moveToNext());
        cForSearch.close();
    }
    public static String getPrice(){
        String queryString = "SELECT "  + tablePRICE + "." + columnPRICE
                + " FROM " + tablePRICE + " INNER JOIN " + tableCAR
                + " ON " + tablePRICE + "." + column_ID + "="
                + tableCAR + "." + column_ID
                + " WHERE ";
        for(int i = 0; i < choice.size(); i++){
            queryString += tableCAR + "." + columnNAMES.get(i) + "='"
                    + choice.get(i) + "'";
            if(i != choice.size() - 1)
                queryString += " and ";
        }
        cForSearch = db.rawQuery(queryString, null);
        cForSearch.moveToFirst();
        return cForSearch.getString(cForSearch.getColumnIndex(columnPRICE)) + "$";
    }
    public static void addData() {
        db = dbh.getWritableDatabase();
        ContentValues cvCAR = new ContentValues();
        ContentValues cvPRICE = new ContentValues();
        for (int i = 0; i < columnNAMES.size(); i++) {
            cvCAR.put(columnNAMES.get(i), addBuffer.get(i));
        }
        cvPRICE.put(columnPRICE, addBuffer.get(columnNAMES.size()));
        db.insert(tableCAR, null, cvCAR);
        db.insert(tablePRICE, null, cvPRICE);
    }
    public static String[] getCONDITION(){
        return CONDITION;
    }
    public static String[] getSALOON(){
        return SALOON;
    }
    public static String[] getCOLOR(){
        return COLOR;
    }
    public static String[] getCAR(){
        return CAR;
    }
    public static int[] getPRICE(){
        return PRICE;
    }
}
