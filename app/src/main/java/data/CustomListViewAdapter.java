package data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.georgesamuel.calcounter.FoodItemDetailsActivity;
import com.example.georgesamuel.calcounter.R;

import java.util.ArrayList;

import model.Food;

public class CustomListViewAdapter extends ArrayAdapter<Food> {

    ArrayList<Food> foodList = new ArrayList<>();
    int layoutResource;
    Context mContext;

    public CustomListViewAdapter(Context context, int resource, ArrayList<Food> data) {
        super(context, resource, data);

        mContext = context;
        layoutResource = resource;
        foodList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Food getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public int getPosition(Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;
        if(row == null || (row.getTag() == null)){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(layoutResource, null);
            holder = new ViewHolder();
            holder.foodName = (TextView) row.findViewById(R.id.name);
            holder.foodCalories = (TextView) row.findViewById(R.id.calories);
            holder.foodDate = (TextView) row.findViewById(R.id.dateText);
            holder.hasChild = (TextView) row.findViewById(R.id.hasChild);

            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }
        holder.food = getItem(position);
        holder.foodName.setText(holder.food.getFoodName());
        holder.foodCalories.setText(String.valueOf(holder.food.getCalories()));
        holder.foodDate.setText(holder.food.getRecordDate());

        final ViewHolder finalHolder = holder;
        holder.hasChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, FoodItemDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("foodObject", finalHolder.food);
                i.putExtras(bundle);
                mContext.startActivity(i);
            }
        });

        return row;
    }

    public class ViewHolder{
        Food food;
        TextView foodName;
        TextView foodCalories;
        TextView foodDate;
        TextView hasChild;
    }
}
