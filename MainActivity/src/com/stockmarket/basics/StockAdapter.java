package com.stockmarket.basics;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.stockmarket.R;


public class StockAdapter extends ArrayAdapter<Stock> {

	private Context context;

    public StockAdapter(Context context, int textViewResourceId, ArrayList<Stock> items) {
        super(context, textViewResourceId, items);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listviewrow, null);
        }

        Stock item = getItem(position);
        if (item!= null) {

        	TextView nameView = (TextView) view.findViewById(R.id.name);
            if (nameView != null) {
                nameView.setText(item.name);
            }
            
            TextView variationView = (TextView) view.findViewById(R.id.variation);
            if (variationView != null) {
            	variationView.setText(Float.toString(item.variation));
            }
            
            TextView lowView = (TextView) view.findViewById(R.id.low);
            if (lowView != null) {
            	lowView.setText(Float.toString(item.lowprice));
            }
            
            TextView highView = (TextView) view.findViewById(R.id.high);
            if (highView != null) {
            	highView.setText(Float.toString(item.highprice));
            }
            
         }

        return view;
    }
}