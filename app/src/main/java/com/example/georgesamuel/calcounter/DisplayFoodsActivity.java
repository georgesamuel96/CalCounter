package com.example.georgesamuel.calcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListViewAdapter;
import data.DatabaseHandler;
import model.Food;
import util.Utils;

public class DisplayFoodsActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<Food> foodList = new ArrayList<>();
    private CustomListViewAdapter adapter;
    private ListView listView;
    private Food myFood;
    private TextView totalCals, totalFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods);

        totalFoods = (TextView) findViewById(R.id.totalItemsTextView);
        totalCals = (TextView) findViewById(R.id.totalAmountTextView);
        listView = (ListView) findViewById(R.id.list);

        refreshData();
    }

    private void refreshData() {
        foodList.clear();
        dba = new DatabaseHandler(getApplicationContext());
        ArrayList<Food> foodFromDB = dba.getFoods();
        int calValue = dba.getTotalCalories();
        int totalItems = dba.getTotalItems();
        totalFoods.setText("Total Food: " + Utils.formatNumber(totalItems));
        totalCals.setText("Total Calories: " + Utils.formatNumber(calValue));

        for(int i = 0; i < foodFromDB.size(); i++){
            String name = foodFromDB.get(i).getFoodName();
            String dateText = foodFromDB.get(i).getRecordDate();
            int cals = foodFromDB.get(i).getCalories();
            int id = foodFromDB.get(i).getFoodId();

            myFood = new Food();
            myFood.setFoodName(name);
            myFood.setCalories(cals);
            myFood.setRecordDate(dateText);
            myFood.setFoodId(id);
            foodList.add(myFood);
        }
        dba.close();
        adapter = new CustomListViewAdapter(getApplicationContext(), R.layout.list_row, foodList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
