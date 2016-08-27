package com.hellostock.app.utils;

import java.util.List;
import java.util.Map;

public class MACDUtil {
	
	public static double getEMA(double preEma,double closePrice,double x){
		double ema = preEma * ((x - 1) / (x + 1)) + closePrice * (2 / (x + 1));
		return ema;
	}
	
	public static double getDIF(double shortEma,double longEma){
		return shortEma - longEma;
	}
	
	public static double getDEA(double preDea, double dif ,double x){
		double dea = preDea * ((x - 1) / (x + 1)) + dif * (2 / (x + 1));
		return dea;
		
	}
	
	public static double getBAR(double dif, double dea){
		return 2 * (dif-dea);
	}
	
	public static void getMacd(List<Map<String, Object>> list,
			int shortEma, int longEma, int mid){
		
		Map<String, Object> map_1 = list.get(0);
		double close1 = (Double) map_1.get("close");
//		MACD m1 = new MACD();
//		m1.setShortEma(getEMA(911.36, closePrice1, shortEma));
//		m1.setLongEma(getEMA(899.53, closePrice1, longEma));
//		m1.setDif(getDIF(m1.getShortEma(), m1.getLongEma()));
//		m1.setDea(getDEA(8.88, m1.getDif(), mid));
//		m1.setBar(getBAR(m1.getDif(), m1.getDea()));
//		map_1.put("macd", m1);
//		m1.setShortEma(closePrice1);
//		m1.setLongEma(closePrice1);
//		m1.setDif(0.0);
//		m1.setDea(0.0);
//		m1.setBar(0.0);
		map_1.put("sema", close1);
		map_1.put("lema", close1);
		map_1.put("dif", 0.0);
		map_1.put("dea", 0.0);
		map_1.put("bar", 0.0);
		
		for (int i = 1;i < list.size();i++) {
			
			Map<String, Object> pre_map = list.get(i - 1);
			Map<String, Object> map = list.get(i);
			
			double close = (Double) map.get("close");
			double pre_sema = (Double) pre_map.get("sema");
			double pre_lema = (Double) pre_map.get("lema");
			double pre_dea = (Double) pre_map.get("dea");
			double sema = getEMA(pre_sema, close, shortEma);
			double lema = getEMA(pre_lema, close, longEma);
			double dif = sema - lema;
			double dea = getDEA(pre_dea, dif, mid);
			
			map.put("sema", sema);
			map.put("lema", lema);
			map.put("dif", dif);
			map.put("dea", dea);
			map.put("bar", getBAR(dif, dea));
			
		}
	}

}
