package com.stockmarket.main;

import java.util.ArrayList;

import com.stockmarket.basics.Stock;

import android.app.Application;

public class StockManager extends Application{

	public ArrayList<Stock> mystocks ;
	public ArrayList<Stock> explorestocks ;
	
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
	
}
