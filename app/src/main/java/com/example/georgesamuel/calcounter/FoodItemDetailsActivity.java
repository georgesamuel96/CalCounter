package com.example.georgesamuel.calcounter;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class FoodItemDetailsActivity extends AppCompatActivity {

    private TextView foodName, calories, dateTaken;
    private Button shareBtn;
    private int foodId;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_details);

        foodName = (TextView) findViewById(R.id.detsFoodName);
        calories = (TextView) findViewById(R.id.detsCaloriesValue);
        dateTaken = (TextView) findViewById(R.id.detsDateText);
        shareBtn = (Button) findViewById(R.id.detsShareButton);

        food = (Food) getIntent().getSerializableExtra("foodObject");

        foodName.setText(food.getFoodName());
        calories.setText(Integer.toString(food.getCalories()));
        dateTaken.setText(food.getRecordDate());

        foodId = food.getFoodId();

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedCals();
            }
        });

    }

    public void sharedCals(){
        StringBuilder dataString = new StringBuilder();
        String name = food.getFoodName();
        String cals = Integer.toString(food.getCalories());
        String date = food.getRecordDate();
        dataString.append(" Food: " + name + "\n");
        dataString.append(" Calories: " + cals + "\n");
        dataString.append(" Eaten on: " + date + "\n");

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "My Caloric Intake");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"george.samuel90@yahoo.com"});
        i.putExtra(Intent.EXTRA_TEXT, dataString.toString());

        try {
            startActivity(Intent.createChooser(i, "Send Email"));
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(getApplicationContext(), "Application not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.delete){
            AlertDialog.Builder alert = new AlertDialog.Builder(FoodItemDetailsActivity.this);
            alert.setTitle("Delete Item");
            alert.setMessage("Are you sure to delete this item ?");
            alert.setNegativeButton("NO", null);
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.deleteFood(foodId);
                    Toast.makeText(getApplicationContext(), "Food Item Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FoodItemDetailsActivity.this, DisplayFoodsActivity.class));
                    FoodItemDetailsActivity.this.finish();
                }
            });
            alert.show();
        }
        return true;
    }
}
