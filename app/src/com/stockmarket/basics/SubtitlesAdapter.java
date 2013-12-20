package com.stockmarket.basics;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.stockmarket.R;
import com.stockmarket.main.CanvasView;

public class SubtitlesAdapter extends ArrayAdapter<Stock> {

	
	private Context context;
	int fragposition;
	Application app;
	
	public SubtitlesAdapter(Context context, int fragnumber, Application a,ArrayList<Stock> items) {
		super(context, R.layout.subtitlerow, items);
        this.context = context;
        this.fragposition = fragnumber;
        app = a;
    }

	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.subtitlerow, null);
        }
		
		Stock s = ((StockManager) app).mystocks.get(position);
		
		View v = (View) view.findViewById(R.id.color);
		v.setBackgroundColor(s.color);
		
		TextView title = (TextView) view.findViewById(R.id.title);
		String sub;
		if(((StockManager) app).graphtype)
			sub = ((StockManager) app).getPercent(position);
		else
		{
			float a = ((StockManager) app).mystocks.get(position).highprice;
			a = a *((StockManager) app).mystocks.get(position).owned;
			sub = ((Float)a ).toString()+"€";
		}
		title.setText(s.name + "     " + sub);
		
		return view;
	}



}
