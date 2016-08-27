package com.hellostock.app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
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
				String date = c.get(0);
				String open = c.get(1);
				String high = c.get(2);
				String low = c.get(3);
				String close = c.get(4);
				String volume = c.get(5);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("date", DateUtil.format2Int(DateUtil.format(date, DateUtil.STR_DATE_PATTERN2)));
				map.put("open", StringUtil.format2Double(open));
				map.put("high", StringUtil.format2Double(high));
				map.put("low", StringUtil.format2Double(low));
				map.put("close", StringUtil.format2Double(close));
				map.put("volume", StringUtil.format2Long(volume));
				map.put("b/s", "");
				map.put("profit", 0.0);
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
			if (alreadyExists) {
				new File(outputFilePath).delete();
			}
			csvOutput.write("date");
			csvOutput.write("open");
			csvOutput.write("high");
			csvOutput.write("low");
			csvOutput.write("close");
			csvOutput.write("volume");
			csvOutput.write("sema");
			csvOutput.write("lema");
			csvOutput.write("dif");
			csvOutput.write("dea");
			csvOutput.write("bar");
			csvOutput.write("fit");
			csvOutput.write("b/s");
//				csvOutput.write("");
			csvOutput.endRecord();
			// else assume that the file already has the correct header line
			// write out a few records
			for (Map<String, Object> map : list) {
//				csvOutput.writeRecord((String[]) map.values().toArray());
				csvOutput.write(Integer.toString((Integer) map.get("date")));
				csvOutput.write(map.get("open").toString());
				csvOutput.write(map.get("high").toString());
				csvOutput.write(map.get("low").toString());
				csvOutput.write(map.get("close").toString());
				csvOutput.write(map.get("volume").toString());
				csvOutput.write(map.get("sema").toString());
				csvOutput.write(map.get("lema").toString());
				csvOutput.write(map.get("dif").toString());
				csvOutput.write(map.get("dea").toString());
				csvOutput.write(map.get("bar").toString());
				csvOutput.write(map.get("fit") == null ? "0":map.get("fit").toString());
				csvOutput.write(map.get("b/s").toString());
				
				csvOutput.endRecord();
			}
			csvOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
