package com.hellostock.app.domain;

import java.io.Serializable;

public class MACD implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double shortEma;
	private double longEma;
	private double dif;
	private double dea;
	private double bar;
	
	public double getShortEma() {
		return shortEma;
	}
	public void setShortEma(double shortEma) {
		this.shortEma = shortEma;
	}
	public double getLongEma() {
		return longEma;
	}
	public void setLongEma(double longEma) {
		this.longEma = longEma;
	}
	public double getDif() {
		return dif;
	}
	public void setDif(double dif) {
		this.dif = dif;
	}
	public double getDea() {
		return dea;
	}
	public void setDea(double dea) {
		this.dea = dea;
	}
	public double getBar() {
		return bar;
	}
	public void setBar(double bar) {
		this.bar = bar;
	}
	@Override
	public String toString() {
		return "MACD [shortEma=" + shortEma + ", longEma=" + longEma + ", dif=" + dif + ", dea=" + dea + ", bar=" + bar
				+ "]";
	}

}
