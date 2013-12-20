package com.stockmarket.basics;

import java.io.Serializable;
import java.util.Random;

import android.graphics.Color;

public class Stock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -143614910170810628L;
	
	
	public String name;
	public String shortname;
	public float highprice;
	public float lowprice;
	public float openprice;
	public float variation;
	public int owned;
	public int color;
	
	public Stock (String name, String shortname, float highprice, float lowprice, float openprice, float variation ,  int owned){
		this.name = name;
		this.shortname = shortname;
		this.highprice = highprice;
		this.lowprice = lowprice;
		this.openprice = openprice;
		this.variation = variation;
		this.owned = owned;
		
		Random c = new Random();		
		this.color = Color.rgb(c.nextInt(256),c.nextInt(256),c.nextInt(256));
		
	}

	
}
