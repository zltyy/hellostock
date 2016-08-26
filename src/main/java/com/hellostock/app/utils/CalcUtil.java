package com.hellostock.app.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CalcUtil {

	/**
	 * 
	 * @param list
	 * @param k
	 * @return
	 */
	public static void calc(List<Map<String, Object>> list, int k){
		List<Map<String, Object>> tradeList = new ArrayList<Map<String, Object>>();
		for (int i = k; i < list.size() - 1; i++) {
			Map<String, Object> pre_map = list.get(i - 1);
			Map<String, Object> cur_map = list.get(i);
			double pre_bar = (Double) pre_map.get("bar");
			double cur_bar = (Double) cur_map.get("bar");
			
			if(cur_bar > 0){
				
			}
			if (calcBad(pre_bar, cur_bar)) {
				System.out.println("bad cross:" + i);
				tradeList.add(pre_map);
//				return k;
			}
			if(calcGolden(pre_bar, cur_bar)){
				System.out.println("golden cross:" + i);
				tradeList.add(pre_map);
			}
		}
		
	}
	
	/**
	 * 
	 * @param cycleList
	 * @param map
	 */
	public static void calcBuy(List<Map<String, Object>> cycleList, Map<String, Object> map) {
		sortPrice(cycleList);
		Map<String, Object> minMap = cycleList.get(0);
		Map<String, Object> maxMap = cycleList.get(cycleList.size() - 1);
		double minPrice = (Double) minMap.get("closePrice");
		double maxPrice = (Double) maxMap.get("closePrice");
		double radio = 0.0;
		double COST = minPrice * (STAMPTAX + COMMISSION + 1);
		radio = maxPrice * (1 - COMMISSION) - COST;
		if (radio <= 0) {
			radio = 0.0;
		} 
		map.put("radio", radio);
	}
	
	public static void calcSell(){
		
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
			double closePrice = 0;
			for (int j = i;j > i - x;j--) {
				closePrice += (Double)list.get(j).get("closePrice");
			}
			map.put(new String().valueOf(x), closePrice / x);
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
