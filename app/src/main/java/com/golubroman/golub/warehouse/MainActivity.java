package com.golubroman.golub.warehouse;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.golubroman.golub.warehouse.DataBase.DBQueries;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements AddDialog.AddDialogListener{
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    CircleIndicator indicator;
    FloatingActionButton basket, add, cancel;
    MaterialSpinner condition_spinner, saloon_spinner, color_spinner, car_spinner;
    View condition_spinner_layout, saloon_spinner_layout, color_spinner_layout, car_spinner_layout;
    TextView condition_spinner_text, saloon_spinner_text, color_spinner_text, car_spinner_text;
    ImageView condition_spinner_tip, saloon_spinner_tip, color_spinner_tip, car_spinner_tip;
    LinearLayout condition_spinner_linear, saloon_spinner_linear, color_spinner_linear, car_spinner_linear;
    Toolbar toolbar;
    android.app.FragmentManager fragmentManager;
    android.app.FragmentTransaction fragmentTransaction;
    DBQueries dbQueries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();

    }

    private void initialization(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager)findViewById(R.id.pager);
        pagerAdapter = new MyPagerAdapter(this.getSupportFragmentManager());
        indicator = (CircleIndicator) findViewById(R.id.pager_indicator);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {}
            @Override
            public void onPageScrollStateChanged(int state) {}});
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);
        fragmentManager = getFragmentManager();
        condition_spinner_layout = findViewById(R.id.condition_spinner_layout);
        saloon_spinner_layout = findViewById(R.id.saloon_spinner_layout);
        color_spinner_layout = findViewById(R.id.color_spinner_layout);
        car_spinner_layout = findViewById(R.id.car_spinner_layout);
        condition_spinner_tip = (ImageView) condition_spinner_layout.findViewById(R.id.tip);
        saloon_spinner_tip = (ImageView) saloon_spinner_layout.findViewById(R.id.tip);
        color_spinner_tip = (ImageView) color_spinner_layout.findViewById(R.id.tip);
        car_spinner_tip = (ImageView) car_spinner_layout.findViewById(R.id.tip);
        condition_spinner = (MaterialSpinner)condition_spinner_layout.findViewById(R.id.spinner);
        saloon_spinner = (MaterialSpinner)saloon_spinner_layout.findViewById(R.id.spinner);
        color_spinner = (MaterialSpinner)color_spinner_layout.findViewById(R.id.spinner);
        car_spinner = (MaterialSpinner)car_spinner_layout.findViewById(R.id.spinner);
        condition_spinner_text = (TextView)condition_spinner_layout.findViewById(R.id.text);
        saloon_spinner_text = (TextView)saloon_spinner_layout.findViewById(R.id.text);
        color_spinner_text = (TextView)color_spinner_layout.findViewById(R.id.text);
        car_spinner_text = (TextView)car_spinner_layout.findViewById(R.id.text);
        condition_spinner_linear = (LinearLayout)condition_spinner_layout.findViewById(R.id.linear);
        saloon_spinner_linear = (LinearLayout)saloon_spinner_layout.findViewById(R.id.linear);
        color_spinner_linear = (LinearLayout)color_spinner_layout.findViewById(R.id.linear);
        car_spinner_linear = (LinearLayout)car_spinner_layout.findViewById(R.id.linear);
        basket = (FloatingActionButton)findViewById(R.id.basket);
        add = (FloatingActionButton)findViewById(R.id.add);
        cancel = (FloatingActionButton)findViewById(R.id.cancel);
        dbQueries = new DBQueries();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main, dbQueries);
        fragmentTransaction.commit();
        condition_spinner_tip.setBackground(getResources().getDrawable(R.drawable.condition_tip));
        saloon_spinner_tip.setBackground(getResources().getDrawable(R.drawable.saloon_tip));
        color_spinner_tip.setBackground(getResources().getDrawable(R.drawable.color_tip));
        car_spinner_tip.setBackground(getResources().getDrawable(R.drawable.car_tip));
        condition_spinner.setItems(Arrays.asList("Choose...", "New", "Used"));
        condition_spinner.setHint("Choose...");
        saloon_spinner.setHint("Choose...");
        color_spinner.setHint("Choose...");
        car_spinner.setHint("Choose...");
        condition_spinner_text.setText("Condition");
        saloon_spinner_text.setText("Saloon");
        color_spinner_text.setText("Color");
        car_spinner_text.setText("Car");
        condition_spinner.setClickable(true);
        saloon_spinner.setClickable(false);
        color_spinner.setClickable(false);
        car_spinner.setClickable(false);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.basket);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        condition_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DBQueries.getData(item.toString());
                saloon_spinner.setItems(DBQueries.stringsForSpinner);
                condition_spinner.setBackgroundColor(getResources().getColor(R.color.foregroundApp));
                condition_spinner.setClickable(false);
                saloon_spinner.setClickable(true);
            }
        });
        saloon_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DBQueries.getData(item.toString());
                color_spinner.setItems(DBQueries.stringsForSpinner);
                saloon_spinner.setBackgroundColor(getResources().getColor(R.color.foregroundApp));
                saloon_spinner.setClickable(false);
                color_spinner.setClickable(true);
            }
        });
        color_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DBQueries.getData(item.toString());
                car_spinner.setItems(DBQueries.stringsForSpinner);
                color_spinner.setBackgroundColor(getResources().getColor(R.color.foregroundApp));
                color_spinner.setClickable(false);
                car_spinner.setClickable(true);
            }
        });
        car_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                car_spinner.setBackgroundColor(getResources().getColor(R.color.foregroundApp));
                DBQueries.choice.add(item.toString());
                car_spinner.setClickable(false);
            }
        });
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = DBQueries.getPrice();
                Toast.makeText(MainActivity.this, DBQueries.choice.get(DBQueries.choice.size()-1)
                        + " was bought!\n Total price: " + price, Toast.LENGTH_LONG).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBQueries.choice = new ArrayList<>();
                condition_spinner.setClickable(true);
                saloon_spinner.setClickable(false);
                color_spinner.setClickable(false);
                car_spinner.setClickable(false);
                condition_spinner.setSelectedIndex(0);
                saloon_spinner.setSelectedIndex(0);
                color_spinner.setSelectedIndex(0);
                car_spinner.setSelectedIndex(0);
                condition_spinner.setBackgroundColor(getResources().getColor(R.color.white));
                saloon_spinner.setBackgroundColor(getResources().getColor(R.color.white));
                color_spinner.setBackgroundColor(getResources().getColor(R.color.white));
                car_spinner.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog dialog = new AddDialog();
                dialog.show(getFragmentManager(), "units");
            }
        });
    }

    @Override
    public void onAddItem(DialogInterface dialogInterface, String condition, String saloon, String color, String car, String price) {
            DBQueries.addBuffer = new ArrayList<>();
            DBQueries.addBuffer.add(condition);
            DBQueries.addBuffer.add(saloon);
            DBQueries.addBuffer.add(color);
            DBQueries.addBuffer.add(car);
            DBQueries.addBuffer.add(price);
            DBQueries.addData();
    }
}
