package com.hellostock.app.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CalcUtil {

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
				int hqdate1 = (Integer) o1.get("hqDateTime");
				int hqdate2 = (Integer) o2.get("hqDateTime");
				if (hqdate1 > hqdate2) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}
}
