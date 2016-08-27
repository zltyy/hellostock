package com.hellostock.app.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CalcUtil {
	
	private static double STAMPTAX = 0.001;
	private static double COMMISSION = 0.0003;
	
	public static double TOTAL = 10000.0;
	public static double COST = 0.;
	public static double PROFIT = 0.;

	/**
	 * 
	 * @param list
	 * @param k
	 * @return
	 */
	public static void calc(List<Map<String, Object>> list, int k){
		boolean b = true;
		double bar = 0;
		List<Map<String, Object>> tradeList = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < list.size() - 1; i++) {
			Map<String, Object> pre_map = list.get(i - 1);
			Map<String, Object> cur_map = list.get(i);
			double pre_bar = (Double) pre_map.get("bar");
			double cur_bar = (Double) cur_map.get("bar");
			
			if (calcBad(pre_bar, cur_bar) && !b) {
				if(k < 15){
					continue;
				}
				b = true;
//				System.out.println("bad cross:" + pre_bar);
				calcSell(cur_map);
				COST = 0;
				PROFIT = 0;
			}
			
			if(Math.abs(pre_bar) > Math.abs(cur_bar)*4 && !b){
				if(calcSell(cur_map)){
					b = true;
					COST = 0;
					PROFIT = 0;
				}
				
			}
			
			if(calcGolden(pre_bar, cur_bar) && b){
//				System.out.println("golden cross:" + i);
				calcBuy(cur_map);
				k = 1;
				bar = cur_bar;
				b = false;
			}
			if(pre_bar > 0){
				k ++;
			}
			
		}
		
	}
	
	/**
	 * 
	 * @param cycleList
	 * @param map
	 */
	public static void calcBuy(Map<String, Object> map) {
		double close = (Double) map.get("close");
		COST =  close;
		map.put("b/s", "b");
	}
	
	public static boolean calcSell(Map<String, Object> map){
		PROFIT = ((Double) map.get("close") / COST) - COMMISSION - (1 + STAMPTAX + COMMISSION);
		if(PROFIT > 0){
			map.put("b/s", "s");
			map.put("fit", PROFIT);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param close
	 * @param stampTax
	 * @param commission
	 * @return
	 */
	public static double calcCost(double close,double stampTax,double commission){
		double cost = close * (1 + stampTax + commission);
		return cost;
	}
	
	/**
	 * 
	 * @param list
	 * @param x
	 */
	public static void calcAvg(List<Map<String, Object>> list,int x){
		for (int i = x - 1;i < list.size();i++) {
			Map<String, Object> map = list.get(i);
			double close = 0;
			for (int j = i;j > i - x;j--) {
				close += (Double)list.get(j).get("close");
			}
			map.put(new String().valueOf(x), close / x);
		}
	}
	
	/**
	 * dead cross
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean calcBad(double b1, double b2){
		if(b1 > b2 && b1 >= 0 && b2 < 0){
			return true;
		}
		return false;
	}
	
	/**
	 * golden cross
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean calcGolden(double b1, double b2){
		if(b2 > b1 && b1 <= 0 && b2 > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param list
	 */
	public static void sortDateAsc(List<Map<String, Object>> list){
		Collections.sort(list, new Comparator<Map<String, Object>>() {

			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int hqdate1 = (Integer) o1.get("date");
				int hqdate2 = (Integer) o2.get("date");
				if (hqdate1 > hqdate2) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}
	
	/**
	 * 
	 * @param list
	 */
	public static void sortPrice(List<Map<String, Object>> list){
		Collections.sort(list, new Comparator<Map<String, Object>>() {

			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				double close1 = (Double) o1.get("close");
				double close2 = (Double) o2.get("close");
				if (close1 > close2) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}
	
	
}
