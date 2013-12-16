package com.stockmarket.main;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stockmarket.R;
import com.stockmarket.basics.CallServiceTask;
import com.stockmarket.basics.RestClient;
import com.stockmarket.basics.Stock;
import com.stockmarket.basics.StockAdapter;

public class MainActivity extends Activity implements RestClient{
	
	private ArrayList<Stock> stockarray;
	private String symbols ="AA+HPQ+AIG+BA+DIS+CAT+T+INTC+AXP+MSFT+HON+GE+UTX+MMM+MRK+IBM+WMT+HD+MO+VZ+XOM+PG+JPM+DD+MCD+PFE+JNJ+C+KO+GM";
	private String url = "http://finance.yahoo.com/d/quotes?f=sghw1&s=" + symbols;
	
	//TODO XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	String FILENAME = "hello_file";
	
	private void savestocks()
	{

		try {
			FileOutputStream fos;
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutput o = new ObjectOutputStream(baos);
			o.writeObject(stockarray);
			byte[] bs = baos.toByteArray();
			fos.write(bs);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void loadstocks()
	{/*
		FileInputStream fis = new FileInputStream(FILENAME);
		fis.read(buffer)
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = new ObjectInputStream(bis);
		Object o = in.readObject(); 
		*/
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//TODO SALVAR E CARREGAR DE LOCAL SOURCE
		
		/*
		stockarray = new ArrayList<Stock>();
		
		//TEST XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		stockarray.add(new Stock("GOOGLE", "GOOG",100, 10, 1, 10,0));
		stockarray.add(new Stock("YAHOO", "YHO",100, 10, 1, 10,0));
		stockarray.add(new Stock("AMAZON", "AMZN",100, 10, 1, 10,0));
		*/
		
		
		CallServiceTask cst = new CallServiceTask(this);
		cst.execute(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackgroundTaskCompleted(String result) {
		if(result!= "" && result != null)
		{
			stockarray = new ArrayList<Stock>();
			String[] s = result.split("\n");
			for (String entry : s) {
				String[] stockrow = entry.split(",");
				stockrow[0] = stockrow[0].replaceAll("\"","");
				stockrow[3] = stockrow[3].substring(5,stockrow[3].length()-3);
				//TODO XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX GET OPENPRICE and other shit
				
				Stock stk = new Stock(stockrow[0], stockrow[0], Float.parseFloat(stockrow[2]), Float.parseFloat(stockrow[1]), 0, Float.parseFloat(stockrow[3]), 0);
				stockarray.add(stk);
			}
			
			ListView l = (ListView)findViewById(R.id.list);
			l.setAdapter(new StockAdapter(this, R.id.empty, stockarray));
			
			l.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					callDetails(arg2);
				}
			});
			
		}
		
		TextView v = (TextView)findViewById(R.id.empty);
		
		if(stockarray.isEmpty())
		{
			v.setVisibility(View.VISIBLE);
		}
		else
		{
			v.setVisibility(View.GONE);
		}
		
		return;
	}
	
	
	void callDetails(int position){
		Intent i = new Intent(this, DetailsComp.class);
		i.putExtra("name", stockarray.get(position).getName());
		startActivity(i);
	}

}
