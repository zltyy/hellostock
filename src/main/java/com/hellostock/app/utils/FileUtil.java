package com.hellostock.app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class FileUtil {
	
	public static void readDataFromCsv(List<Map<String, Object>> list, String filePath) {
		try {
			CsvReader c = new CsvReader(filePath);
			c.readHeaders();
			while (c.readRecord()) {
				c.getRawRecord();
				String date = c.get("日期");
				String open = c.get("开盘价");
				String high = c.get("最高价");
				String low = c.get("最低价");
				String close = c.get("收盘价");
				String volume = c.get("成交量");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("date", DateUtil.format2Int(DateUtil.format(date, DateUtil.STR_DATE_PATTERN2)));
				map.put("open", StringUtil.format2Double(open));
				map.put("high", StringUtil.format2Double(high));
				map.put("low", StringUtil.format2Double(low));
				map.put("close", StringUtil.format2Double(close));
				map.put("volume", StringUtil.format2Long(volume));
				list.add(map);
			}
			c.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void writeDate2Csv(List<Map<String, Object>> list, String outputFilePath) {
		// before we open the file check to see if it already exists
		boolean alreadyExists = new File(outputFilePath).exists();
		try {
			// use FileWriter constructor that specifies open for appending
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFilePath, true), ',');
			// if the file didn't already exist then we need to write out the
			// header line
			if (!alreadyExists) {
				for (String key : list.get(0).keySet()) {
					csvOutput.write(key);
				}
				csvOutput.endRecord();
			}
			// else assume that the file already has the correct header line
			// write out a few records
			for (Map<String, Object> map : list) {
				csvOutput.writeRecord((String[]) map.values().toArray());
				/*csvOutput.write(DateUtil.format2String((Date) map.get("hqDateTime"), DateUtil.STR_DATE_PATTERN2));
				csvOutput.write(map.get("openPrice").toString());
				csvOutput.write(map.get("highestPrice").toString());
				csvOutput.write(map.get("lowestPrice").toString());
				csvOutput.write(map.get("closePrice").toString());
				csvOutput.write(map.get("totalAmount").toString());
				MACD macd = (MACD) map.get("macd");
				csvOutput.write(Double.toString(macd.getShortEma()));
				csvOutput.write(Double.toString(macd.getLongEma()));
				csvOutput.write(Double.toString(macd.getDif()));
				csvOutput.write(Double.toString(macd.getDea()));
				csvOutput.write(Double.toString(macd.getBar()));
				csvOutput.write(Double.toString((Double) map.get("radio") == null ? 0.0 : (Double) map.get("radio")));
				double d1 = (Double)map.get(new String().valueOf(m))==null?0:(Double)map.get(new String().valueOf(m));
				double d2 = (Double)map.get(new String().valueOf(n))==null?0:(Double)map.get(new String().valueOf(n));
				csvOutput.write(Double.toString(d1 - d2));
				csvOutput.write(Double.toString(d1));
				csvOutput.write(Double.toString(d2));*/
				csvOutput.endRecord();
			}
			csvOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
