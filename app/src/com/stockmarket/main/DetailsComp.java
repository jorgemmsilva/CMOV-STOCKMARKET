package com.stockmarket.main;

import java.util.Calendar;

import com.example.stockmarket.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.stockmarket.basics.CallServiceTask;
import com.stockmarket.basics.RestClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DetailsComp extends Activity implements RestClient {
	String name;
	boolean detailsVis = false;
	
	String path = "http://ichart.finance.yahoo.com/table.txt?";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_comp);
		
		Intent i = getIntent();
		name = i.getExtras().getString("name");
		
		CallServiceTask cst = new CallServiceTask(this);
		cst.execute(createUrl());
		
	}
	
	String createUrl(){
		String url = "";
		
		Calendar date = Calendar.getInstance();
		int d = date.get(Calendar.MONTH)-1;
		int e = date.get(Calendar.DAY_OF_MONTH);
		int f = date.get(Calendar.YEAR);
		
		date.add(Calendar.MONTH, -1);
		
		int a = date.get(Calendar.MONTH)-1;
		int b = date.get(Calendar.DAY_OF_MONTH);
		int c = date.get(Calendar.YEAR); 
		
		String url_date = "a="+a+"&b="+b+"&c="+c+"&d="+d+"&e="+e+"&f="+f;
		url_date = url_date + "&g=d";
		
		url = path + url_date+"&s="+name;
		
		return url;
	}

	@Override
	public void onBackgroundTaskCompleted(String result) {
		String lines[] = result.split("\\r?\\n");
		
		String[] dates = new String[4];
		float delta = (lines.length-3)/3;	
		GraphViewData[] data = new GraphViewData[lines.length-1];
		
		int i1 = (int)delta + 1;
		int i2 = i1 + (int)delta;
		
		float max = 0, min = 100000, init = 0, last = 0, dif = 0;
		int volume = 0;
		String dateMax = "", dateMin = "", dateVolume = "", dateDif = "";
		
		for(int i = lines.length-1, j = 0; i > 0; i--){
			String aux[] = lines[i].split(",");
			
			float f = Float.parseFloat(aux[4]);
			data[i-1] = new GraphViewData(lines.length-1-i, f);
			
			if(i == 1 || i1 == i|| i2 == i || i+1 == lines.length){
				String d[] = aux[0].split("-");
				dates[j] = d[2]+"/"+d[1];
				j++;
			}
			
			if(i == 1){
				last = f;
			}else if(i == lines.length-1){
				init = f;
			}
			
			float max_aux = Float.parseFloat(aux[4]);
			if(max_aux > max){
				max = max_aux;
				String d[] = aux[0].split("-");
				dateMax = d[2]+"/"+d[1]+"/"+d[0];
			}
			
			float min_aux = Float.parseFloat(aux[4]);
			if(min_aux < min){
				min = min_aux;
				String d[] = aux[0].split("-");
				dateMin = d[2]+"/"+d[1]+"/"+d[0];
			}
			
			int volume_aux = Integer.parseInt(aux[5]);
			if(volume_aux > volume){
				volume = volume_aux;
				String d[] = aux[0].split("-");
				dateVolume = d[2]+"/"+d[1]+"/"+d[0];
			}
			
			float ma = Float.parseFloat(aux[2]);
			float mi = Float.parseFloat(aux[3]);
			if(ma-mi > dif){
				dif = ma-mi;
				String d[] = aux[0].split("-");
				dateDif = d[2]+"/"+d[1]+"/"+d[0];
			}
		}
		
		
		GraphView graphView = new LineGraphView(this, "LAST MONTH - "+name);
		
		graphView.addSeries(new GraphViewSeries(data));
		graphView.setHorizontalLabels(dates);
		graphView.setViewPort(0, data.length-1);
		graphView.setScrollable(true);
		graphView.setScalable(true);
		graphView.getGraphViewStyle().setTextSize(15);

		
		LinearLayout graphLayout  = (LinearLayout) findViewById(R.id.graphLayout);
		graphLayout.addView(graphView);
		
		TableLayout details = (TableLayout) findViewById(R.id.detailsLayout);
		
		TableRow high = new TableRow(this);
		TextView h1 = new TextView(this);
		h1.setTypeface(null, Typeface.BOLD);
		h1.setText("High");
		TextView h2 = new TextView(this);
		h2.setText(""+max);
		TextView h3 = new TextView(this);
		h3.setText(dateMax);
		h1.setPadding(10, 0, 10, 0);
		h2.setPadding(10, 0, 10, 0);
		h3.setPadding(10, 0, 10, 0);
		h2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		high.addView(h1);
		high.addView(h2);
		high.addView(h3);
		details.addView(high);
		
		TableRow low = new TableRow(this);
		TextView l1 = new TextView(this);
		l1.setText("Low");
		l1.setTypeface(null, Typeface.BOLD);
		TextView l2 = new TextView(this);
		l2.setText(""+min);
		TextView l3 = new TextView(this);
		l3.setText(dateMin);
		l1.setPadding(10, 0, 10, 0);
		l2.setPadding(10, 0, 10, 0);
		l3.setPadding(10, 0, 10, 0);
		l2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		low.addView(l1);
		low.addView(l2);
		low.addView(l3);
		details.addView(low);
		
		TableRow change = new TableRow(this);
		TextView c1 = new TextView(this);
		c1.setText("Change");
		c1.setTypeface(null, Typeface.BOLD);
		TextView c2 = new TextView(this);
		

		float c = ((last-init)/init)*(float)100.0;
		String cs = String.format("%.2f", c);
		
		if(c >= 0){
			c2.setText("+"+cs+"%");
			c2.setTextColor(Color.GREEN);
		}else{
			c2.setText(cs+"%");
			c2.setTextColor(Color.RED);
		}
		
		c1.setPadding(10, 0, 10, 0);
		c2.setPadding(10, 0, 10, 0);
		c2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		
		change.addView(c1);
		change.addView(c2);
		
		details.addView(change);
		
		
		
		
		final TableLayout detail2 = (TableLayout) findViewById(R.id.pLayout);
		
		TableRow vol = new TableRow(this);
		TextView v1 = new TextView(this);
		v1.setTypeface(null, Typeface.BOLD);
		v1.setText("Greater volume");
		TextView v2 = new TextView(this);
		v2.setText(""+volume);
		TextView v3 = new TextView(this);
		v3.setText(dateVolume);
		
		v1.setPadding(10, 0, 10, 0);
		v2.setPadding(10, 0, 10, 0);
		v3.setPadding(10, 0, 10, 0);
		
		vol.addView(v1);
		vol.addView(v2);
		vol.addView(v3);
		
		TableRow di = new TableRow(this);
		TextView d1 = new TextView(this);
		d1.setTypeface(null, Typeface.BOLD);
		d1.setText("Greater change");
		TextView d2 = new TextView(this);
		String cs1 = String.format("%.2f", dif);
		d2.setText(""+cs1);
		TextView d3 = new TextView(this);
		d3.setText(dateDif);
		
		d1.setPadding(10, 0, 10, 0);
		d2.setPadding(10, 0, 10, 0);
		d3.setPadding(10, 0, 10, 0);
		
		di.addView(d1);
		di.addView(d2);
		di.addView(d3);

		detail2.addView(vol);
		detail2.addView(di);
		detail2.setVisibility(View.INVISIBLE);
		
		final Button button = (Button)findViewById(R.id.button);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(detailsVis){
					detailsVis = false;
					button.setText("More details");
					detail2.setVisibility(View.INVISIBLE);
				}else{
					detailsVis = true;
					button.setText("Less details");
					detail2.setVisibility(View.VISIBLE);
				}
			}
		});
	}


}
