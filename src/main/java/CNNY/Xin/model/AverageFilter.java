package CNNY.Xin.model;

import java.util.ArrayList;

public class AverageFilter {

	private Integer filterLength;
	private ArrayList<Double> dataArray;
	
	public AverageFilter(Integer filterLength) {
		
		this.filterLength = filterLength;
		this.dataArray = new ArrayList<>();
	}
	
	public Double filter(Double inputData) {
		
		Double result = 0.0;
		
		dataArray.add(inputData);
		while (dataArray.size() > filterLength) {
			dataArray.remove(0);
		}
		
		for (int i = 0; i < dataArray.size(); i++) {
			result += dataArray.get(i);
		}
		
		result = result / filterLength;
		
		return result;
	}
}
