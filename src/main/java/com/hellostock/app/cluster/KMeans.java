package com.hellostock.app.cluster;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KMeans extends KMeansClustering<Double>{

	@Override
	public double similarScore(Double o1, Double o2) {
		double distance = Math.sqrt((o1 - o2) * (o1 - o2));  
		return distance * -1;
	}

	@Override
	public boolean equals(Double o1, Double o2) {
		if(!o1.isInfinite() && !o2.isInfinite()){
			return o1 == o2; 
		}else{
			return false;
		}
	}
	
	@Override
	public Double getCenterT(List<Double> list) {
		double x = 0;  
		try {  
	        for (double xy : list) {  
	            x += xy;  
	        }  
	        x = x / list.size();  
		 } catch(Exception e) {  
	          
		 }    
		return x; 
	}

	@Override
	public boolean isNaN(List<Double> nowCenter) {
		boolean b = true;
		for (Double d : nowCenter) {
			if(!d.isInfinite() && !d.isNaN()){
				b = false; 
			}else{
				b = true;
				break;
			}
		}
		return b;
	}

	@Override
	public void initCenter(List<Double> dataArray,List<Double> nowCenter, int k) {
		Collections.sort(dataArray, new Comparator<Double>() {

			public int compare(Double o1, Double o2) {
				if(o1 >= o2){
					return 1;
				}else{
					return -1;
				}
			}
		});
		double min = dataArray.get(0);
		double max = dataArray.get(dataArray.size() -1);
		double gap = (max - min) / k;
		for (int i = 1; i < k + 1; i++) {
			nowCenter.add(min + (i * gap));
		}
	}
}
