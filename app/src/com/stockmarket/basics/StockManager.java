package com.stockmarket.basics;

import java.util.ArrayList;
import java.util.Random;

import android.app.Application;
import android.graphics.Color;

public class StockManager extends Application{

	public ArrayList<Stock> mystocks ;
	public ArrayList<Stock> explorestocks ;
	public Boolean graphtype = true;
	
	public StockManager()
	{
		mystocks = new ArrayList<Stock>();
		explorestocks = new ArrayList<Stock>();
	}
	
	public Boolean containsStock( ArrayList<Stock> stocklist, Stock stock)
	{
		for(Stock s : stocklist)
		{
			if(s.name.equals(stock.name))
				return true;
		}
		return false;
	}
	
	private void savestocks()
	{
/*
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
		*/
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
	
	
	
	
	
	public void addToMystocks(Stock item) {
		if(!containsStock( mystocks,item))
		{
			mystocks.add(item);
		}
		
	}

	public void setOwned(String name, Integer i) {
		if(i < 0)
			i = 0;
		for(Stock s : mystocks)
		{
			if(s.name.equals(name))
				s.owned = i;
		}		
	}

	public ArrayList<Float> getChartValues(int charttype) {
		ArrayList<Float> values = new ArrayList<Float>();
		int total = 0;
		for( Stock s : mystocks)
		{
			if(charttype == 1){
				values.add((float) s.owned);
				total+=s.owned;
			}
			else
			{
				values.add(s.highprice*s.owned);
				total+=s.highprice*s.owned;
			}
		}
		
		for( int i = 0; i < values.size(); ++i){
			values.set(i,(values.get(i)/total)*360);
		}
		
		return values;
	}

	public ArrayList<Integer> getChartColors() {
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		for(Stock s : mystocks)
		{
			res.add(s.color);
		}
		
		
		return res;
	}

	public String getPercent(int index) {
		int total = 0;
		for( Stock s : mystocks)
		{
			total+=s.owned;
		}
		
		Float res = (float) ((float)mystocks.get(index).owned/(float)total * 100);
		return res.toString() + "%";
	}

	public void setGraphtype(Boolean g) {
		graphtype = g;
		
	}

	
}
