package com.hellostock.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hellostock.app.utils.CalcUtil;
import com.hellostock.app.utils.DateUtil;
import com.hellostock.app.utils.FileUtil;
import com.hellostock.app.utils.MACDUtil;

/**
 * Hello world!
 *
 */
public class App 
{
	private static double STAMPTAX = 0.001;
	private static double COMMISSION = 0.003;
	public static int min = 5;
	public static int max = 20;

	private static String base_path = "resources\\data\\";

	public static void main(String[] args) {
		File file = new File(base_path);
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for (File f : files) {
				String fileName = f.getName();
				if(fileName.endsWith("csv")){
					List<Map<String, Object>> stock_list = new ArrayList<Map<String, Object>>();
					FileUtil.readDataFromCsv(stock_list, f.getAbsolutePath());
					MACDUtil.getMacd(stock_list, 12, 9, 26);
					CalcUtil.calcAvg(stock_list, min);
					CalcUtil.calcAvg(stock_list, max);
					calculation(stock_list, 0);
					FileUtil.writeDate2Csv(stock_list, f.getAbsolutePath().replace(".csv", "_MACD.csv"));
				}
			}
		}
	}

	/**
	 * 
	 * @param list
	 * @param k
	 * @return
	 */
	public static int calculation(List<Map<String, Object>> list, int k) {
		List<Map<String, Object>> cycleList = new ArrayList<Map<String, Object>>();
		for (int i = k; i < list.size() - 1; i++) {
			Map<String, Object> map1 = list.get(i);
			Map<String, Object> map2 = list.get(i + 1);
			double b1 = (Double) map1.get("bar");
			double b2 = (Double) map2.get("bar");
			if (i == list.size() - 1) {
				k = list.size() - 1;
				return i;
			}
			if (b1 > b2 && b1 >= 0 && b2 < 0) {
				System.out.println("compare:" + i);
				cycleList = list.subList(k, i);
				System.out.println(cycleList.size());
				calcRadio(cycleList, map1);
				k = i + 1;
				calculation(list,k);
				return k;
			}
		}
		return list.size();
	}

	/**
	 * 
	 * @param list
	 */
	public static void sort(List<Map<String, Object>> list) {
		Collections.sort(list, new Comparator<Map<String, Object>>() {

			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				double p1 = (Double) o1.get("closePrice");
				double p2 = (Double) o2.get("closePrice");
				if (p1 > p2) {
					return 1;
				} else if (p1 == p2) {
					int hqDate1 = DateUtil.format2Int((Date) o1.get("hqDateTime"));
					int hqDate2 = DateUtil.format2Int((Date) o2.get("hqDateTime"));
					if (hqDate1 > hqDate2) {
						return 1;
					} else {
						return -1;
					}
				} else {
					return -1;
				}
			}
		});
	}

	/**
	 * 
	 * @param cycleList
	 * @param map
	 */
	public static void calcRadio(List<Map<String, Object>> cycleList, Map<String, Object> map) {
		sort(cycleList);
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
	
}
