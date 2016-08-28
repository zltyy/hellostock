package com.hellostock.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hellostock.app.cluster.KMeans;
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
	public static int min = 5;
	public static int max = 20;

	private static String base_path = "E:\\workspaces\\hellostock\\src\\main\\resources\\data";

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
					Map<String, Integer> map_macd = MACDUtil.statCycleDays(stock_list);
					
//					kmeans(map_macd);
					
//					System.out.println(map_macd);
//					CalcUtil.calcAvg(stock_list, min);
//					CalcUtil.calcAvg(stock_list, max);
					CalcUtil.calc(stock_list, 1);
					double fit = 0;
					for (Map<String, Object> map : stock_list) {
						fit += (Double)map.get("fit")==null ? 0: (Double)map.get("fit");
					}
					System.out.println(f.getName().split(".csv")[0]+":"+fit * 100);
//					FileUtil.writeDate2Csv(stock_list, "d:\\lhcl\\"+f.getName().replace(".csv", "_MACD.csv"));
				}
			}
		}
	}
	
	
	public static void kmeans(Map<String, Integer> map){
		List<Double> up = new ArrayList<Double>();
		List<Double> down = new ArrayList<Double>();
		for (String key : map.keySet()) {
			if(key.contains("up_")){
				up.add((double)(map.get(key)));
			}
			if(key.contains("down_")){
				down.add((double)(map.get(key)));
			}
		}
		KMeans kMeans = new KMeans();
		kMeans.setK(2);
		kMeans.setMaxClusterTimes(1000);
		for (Double d : up) {
			kMeans.addRecord(d);
		}
		List<List<Double>> cluster = kMeans.clustering();
		List<Double> center = kMeans.getClusteringCenterT();
		System.out.println(center);
	}

}
