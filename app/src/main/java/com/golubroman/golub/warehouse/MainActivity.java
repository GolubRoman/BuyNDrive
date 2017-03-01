package com.golubroman.golub.warehouse;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements AddDialog.AddDialogListener{
    @BindView(R.id.pager) ViewPager viewPager;
    PagerAdapter pagerAdapter;
    @BindView(R.id.pager_indicator) CircleIndicator indicator;

    @BindView(R.id.add) FloatingActionButton add;
    @BindView(R.id.cancel) FloatingActionButton cancel;
    @BindView(R.id.basket) FloatingActionButton basket;

    @BindView(R.id.condition_spinner_layout) View condition_spinner_layout;
    @BindView(R.id.saloon_spinner_layout) View saloon_spinner_layout;
    @BindView(R.id.color_spinner_layout) View color_spinner_layout;
    @BindView(R.id.car_spinner_layout) View car_spinner_layout;

    MaterialSpinner condition_spinner, saloon_spinner, color_spinner, car_spinner;
    TextView condition_spinner_text, saloon_spinner_text, color_spinner_text, car_spinner_text;
    ImageView condition_spinner_tip, saloon_spinner_tip, color_spinner_tip, car_spinner_tip;
    LinearLayout condition_spinner_linear, saloon_spinner_linear, color_spinner_linear, car_spinner_linear;


    @BindView(R.id.toolbar) Toolbar toolbar;
    android.app.FragmentManager fragmentManager;
    android.app.FragmentTransaction fragmentTransaction;
    DBQueries dbQueries;

    @BindArray(R.array.condition_values) String[] condition_spinner_values;
    @BindArray(R.array.column_names) String[] spinner_names;
    @BindString(R.string.choose)String choose;
    @BindString(R.string.cars_with_such) String carsWithSuch;

    @BindDrawable(R.drawable.condition_tip) Drawable condition_tip;
    @BindDrawable(R.drawable.saloon_tip) Drawable saloon_tip;
    @BindDrawable(R.drawable.color_tip) Drawable color_tip;
    @BindDrawable(R.drawable.car_tip) Drawable car_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialization();

    }

    private void initialization(){
        pagerAdapter = new MyPagerAdapter(this.getSupportFragmentManager());
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

        /* Binding of MainActivity views with ButterKnife
            */

        condition_spinner_tip = ButterKnife.findById(condition_spinner_layout, R.id.tip);
        saloon_spinner_tip = ButterKnife.findById(saloon_spinner_layout, R.id.tip);
        color_spinner_tip = ButterKnife.findById(color_spinner_layout, R.id.tip);
        car_spinner_tip = ButterKnife.findById(car_spinner_layout, R.id.tip);

        condition_spinner = ButterKnife.findById(condition_spinner_layout, R.id.spinner);
        saloon_spinner = ButterKnife.findById(saloon_spinner_layout, R.id.spinner);
        color_spinner = ButterKnife.findById(color_spinner_layout, R.id.spinner);
        car_spinner = ButterKnife.findById(car_spinner_layout, R.id.spinner);

        condition_spinner_text = ButterKnife.findById(condition_spinner_layout, R.id.text);
        saloon_spinner_text = ButterKnife.findById(saloon_spinner_layout, R.id.text);
        color_spinner_text = ButterKnife.findById(color_spinner_layout, R.id.text);
        car_spinner_text = ButterKnife.findById(car_spinner_layout, R.id.text);

        condition_spinner_linear = ButterKnife.findById(condition_spinner_layout, R.id.linear);
        saloon_spinner_linear = ButterKnife.findById(saloon_spinner_layout, R.id.linear);
        color_spinner_linear = ButterKnife.findById(color_spinner_layout, R.id.linear);
        car_spinner_linear = ButterKnife.findById(car_spinner_layout, R.id.linear);

        dbQueries = new DBQueries();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main, dbQueries);
        fragmentTransaction.commit();

        condition_spinner_tip.setBackground(condition_tip);
        saloon_spinner_tip.setBackground(saloon_tip);
        color_spinner_tip.setBackground(color_tip);
        car_spinner_tip.setBackground(car_tip);

        condition_spinner.setItems(condition_spinner_values);
        condition_spinner.setHint(choose);
        saloon_spinner.setHint(choose);
        color_spinner.setHint(choose);
        car_spinner.setHint(choose);

        condition_spinner_text.setText(spinner_names[0]);
        saloon_spinner_text.setText(spinner_names[1]);
        color_spinner_text.setText(spinner_names[2]);
        car_spinner_text.setText(spinner_names[3]);

        condition_spinner.setClickable(true);
        saloon_spinner.setClickable(false);
        color_spinner.setClickable(false);
        car_spinner.setClickable(false);

        /* ClickListeners for spinners and round buttons on MainActivity
            */

        condition_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DBQueries.getData(item.toString());
                Toast.makeText(MainActivity.this, DBQueries.cntCAR + " " + carsWithSuch, Toast.LENGTH_LONG).show();
                saloon_spinner.setItems(DBQueries.stringsForSpinner);
                condition_spinner.setBackgroundColor(Color.GRAY);
                condition_spinner.setClickable(false);
                saloon_spinner.setClickable(true);
            }
        });
        saloon_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DBQueries.getData(item.toString());
                Toast.makeText(MainActivity.this, DBQueries.cntCAR + " " + carsWithSuch, Toast.LENGTH_LONG).show();
                color_spinner.setItems(DBQueries.stringsForSpinner);
                saloon_spinner.setBackgroundColor(Color.GRAY);
                saloon_spinner.setClickable(false);
                color_spinner.setClickable(true);
            }
        });
        color_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DBQueries.getData(item.toString());
                Toast.makeText(MainActivity.this, DBQueries.cntCAR + " " + carsWithSuch, Toast.LENGTH_LONG).show();
                car_spinner.setItems(DBQueries.stringsForSpinner);
                color_spinner.setBackgroundColor(Color.GRAY);
                color_spinner.setClickable(false);
                car_spinner.setClickable(true);
            }
        });
        car_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                car_spinner.setBackgroundColor(Color.GRAY);
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
                condition_spinner.setBackgroundColor(Color.WHITE);
                saloon_spinner.setBackgroundColor(Color.WHITE);
                color_spinner.setBackgroundColor(Color.WHITE);
                car_spinner.setBackgroundColor(Color.WHITE);
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

    /* Realization of method from interface AddDialogListener
            from AddDialog class
        */

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
