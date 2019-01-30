package com.example.georgesamuel.calcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {

    private EditText foodName, foodCalories;
    Button submitBtn;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);
        foodName = (EditText) findViewById(R.id.foodEditText);
        foodCalories = (EditText) findViewById(R.id.caloriesEditText);
        submitBtn = (Button) findViewById(R.id.submitButton);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDB();
            }
        });
    }

    private void saveDataToDB() {
        Food food = new Food();
        String name = foodName.getText().toString().trim();
        String calories = foodCalories.getText().toString().trim();

        if(name.equals("") || calories.equals("")){
            Toast.makeText(MainActivity.this, "No empty fields allowed", Toast.LENGTH_SHORT).show();
        }
        else {
            food.setFoodName(name);
            int cal = Integer.parseInt(calories);
            food.setCalories(cal);
            dba.addFood(food);
        }
        dba.close();
        foodName.setText("");
        foodCalories.setText("");
        startActivity(new Intent(MainActivity.this, DisplayFoodsActivity.class));
    }
}
