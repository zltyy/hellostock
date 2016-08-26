package com.hellostock.app.Indicator;

import java.util.ArrayList;
import java.util.List;

import com.hellostock.app.domain.StockHq;

public abstract class Indicator {

	protected double value;
	
	protected List<StockHq> list;
	
	public Indicator() {
		list = new ArrayList<StockHq>();
	}
	public Indicator(final List<StockHq> list) {
		this();
		this.list = list;
	}
	
	public abstract double calculate();// must be implemented in subclasses.


}
