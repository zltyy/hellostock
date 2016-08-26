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
	private static double COMMISSION = 0.0003;
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
					MACDUtil.getMacd(stock_list, 12, 26, 9);
					CalcUtil.calcAvg(stock_list, min);
					CalcUtil.calcAvg(stock_list, max);
					CalcUtil.calc(stock_list, 1);
					FileUtil.writeDate2Csv(stock_list, f.getAbsolutePath().replace(".csv", "_MACD.csv"));
				}
			}
		}
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

}
