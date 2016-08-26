package com.hellostock.app.utils;

import java.util.List;
import java.util.Map;

import com.hellostock.app.domain.MACD;


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
			int shortEma, int mid, int longEma){
		
		Map<String, Object> map_1 = list.get(0);
		double closePrice1 = (Double) map_1.get("closePrice");
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
		map_1.put("sema", closePrice1);
		map_1.put("lema", closePrice1);
		map_1.put("dif", 0);
		map_1.put("dea", 0);
		map_1.put("bar", 0);
		
//		Map<String, Object> map_2 = list.get(1);
//		double closePrice2 = (Double) map_2.get("closePrice");
//		MACD m2 = new MACD();
//		m2.setShortEma(closePrice1 + (closePrice2 -closePrice1) * 2/ shortEma);
//		m2.setLongEma(closePrice1 + (closePrice2 -closePrice1) * 2/ longEma);
//		m2.setDif(getDIF(m2.getShortEma(), m2.getLongEma()));
//		m2.setDea(getDEA(m1.getDif(), m2.getDif(), mid));
//		m2.setBar(getBAR(m2.getDif(), m2.getDea()));
//		map_2.put("macd", m2);
		
		for (int i = 1;i < list.size();i++) {
			Map<String, Object> pre_map = list.get(i - 1);
//			MACD pre_macd = (MACD) pre_map.get("macd");
			Map<String, Object> map = list.get(i);
			double close = (Double) map.get("close");
//			MACD macd = new MACD();
//			macd.setShortEma(getEMA(pre_macd.getShortEma(), close, shortEma));
//			macd.setLongEma(getEMA(pre_macd.getLongEma(), close, longEma));
//			macd.setDif(getDIF(macd.getShortEma(), macd.getLongEma()));
//			macd.setDea(getDEA(pre_macd.getDea(), macd.getDif(), mid));
//			macd.setBar(getBAR(macd.getDif(), macd.getDea()));
//			map.put("macd", macd);
			
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
