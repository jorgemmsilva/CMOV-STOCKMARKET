package com.stockmarket.basics;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockmarket.R;
import com.stockmarket.main.StockManager;


public class StockAdapter extends ArrayAdapter<Stock> {

	private Context context;
	int position;
	Application app;
	
    public StockAdapter(Context context, int textViewResourceId, ArrayList<Stock> items,int p, Application a) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.position = p;
        app = a;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listviewrow, null);
        }

        final Stock item = getItem(position);
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
            
			Button plusbtn = (Button)view.findViewById(R.id.plusbtn);
			if(this.position!=1)
			{
				plusbtn.setVisibility(View.GONE);
			}
			
			LinearLayout mystockslayout = (LinearLayout)view.findViewById(R.id.mystockslayout);
			if(this.position!=0)
			{
				mystockslayout.setVisibility(View.GONE);
			}
			else
			{
				TextView mystocks = (TextView)view.findViewById(R.id.mystocks);
				mystocks.setText(((Integer)item.owned).toString());
			}
			
			plusbtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {					
					((StockManager) app).addToMystocks(item);
					Toast toast = Toast.makeText(context, "added to mystocks", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
			});
         }

        return view;
    }
}