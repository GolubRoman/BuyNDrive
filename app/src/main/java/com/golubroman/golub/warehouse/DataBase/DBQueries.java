package com.golubroman.golub.warehouse.DataBase;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import com.golubroman.golub.warehouse.MainActivity;
import com.golubroman.golub.warehouse.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import butterknife.ButterKnife;

/**
 * Created by User on 25.02.2017.
 */

public class DBQueries extends Fragment {
    protected static String[] CONDITION;
    protected static String[] SALOON;
    protected static String[] COLOR;
    protected static String[] CAR;
    protected static String[] PRICE;
    public static List<String> addBuffer;
    final static String tableCAR = "tableCAR";
    final static String tablePRICE = "tablePRICE";
    final static String columnPRICE = "columnPRICE";
    final static String column_ID = "_ID";
    public static List<String> choice;
    final static List<String> columnNAMES =
            Arrays.asList("columnCONDITION", "columnSALOON", "columnCOLOR", "columnCAR");
    public static int cntCAR;
    private static DBHelper dbh;
    private static SQLiteDatabase db;
    private static Cursor cForSearch;
    public static List<String> stringsForSpinner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());

        /* Getting object of DBHelper class for
            further writing in the database
            */

        dbh = new DBHelper(getContext());
        db = dbh.getWritableDatabase();
        CONDITION = getResources().getStringArray(R.array.condition);
        SALOON = getResources().getStringArray(R.array.saloon);
        COLOR = getResources().getStringArray(R.array.color);
        CAR = getResources().getStringArray(R.array.car);
        cntCAR = CAR.length;
        choice = new ArrayList<>();


    }

    /* Method for getting response from database with
            query built with users parameters
            */
    public static void getData(String currentChoice){
        choice.add(currentChoice);

        /* Building query with users parameters
            */

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

        /* Initializing of class Cursor object
            with response from database
            */

        cForSearch = db.rawQuery(queryString, null);
        stringsForSpinner = new ArrayList<>();
        cForSearch.moveToFirst();
        cntCAR = cForSearch.getCount();

        /* Gathering information from database response
            for using them in the next spinner
            */

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

    /* Method for joining two tables for getting
        field price from table Price, which bound
        with the field car from table Car
            */

    public static String getPrice(){
        String price;

        /* Building query with users parameters
            for joining two tables
            */

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
        price = cForSearch.getString(cForSearch.getColumnIndex(columnPRICE)) + "$";;
        cForSearch.close();
        return price;
    }

    /* Method for adding car and its price
        to database
            */

    public static void addData() {
        db = dbh.getWritableDatabase();
        ContentValues cvCAR = new ContentValues();
        ContentValues cvPRICE = new ContentValues();

        /* Creating class ContentValues object for
            further pushing it in database
            */

        for (int i = 0; i < columnNAMES.size(); i++) {
            cvCAR.put(columnNAMES.get(i), addBuffer.get(i));
        }
        cvPRICE.put(columnPRICE, addBuffer.get(columnNAMES.size()));
        db.insert(tableCAR, null, cvCAR);
        db.insert(tablePRICE, null, cvPRICE);
    }
}
