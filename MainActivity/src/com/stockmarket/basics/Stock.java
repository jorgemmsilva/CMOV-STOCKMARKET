package com.stockmarket.basics;

import java.io.Serializable;

public class Stock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -143614910170810628L;
	
	
	String name;
	String shortname;
	float highprice;
	float lowprice;
	float openprice;
	float variation;
	int owned;
	
	public Stock (String name, String shortname, float highprice, float lowprice, float openprice, float variation ,  int owned){
		this.name = name;
		this.shortname = shortname;
		this.highprice = highprice;
		this.lowprice = lowprice;
		this.openprice = openprice;
		this.variation = variation;
		this.owned = owned;
	}
	
	public String getName(){
		return name;
	}

	
}
