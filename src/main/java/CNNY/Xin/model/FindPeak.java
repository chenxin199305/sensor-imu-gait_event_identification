package CNNY.Xin.model;

import java.util.ArrayList;

public class FindPeak {

	/**
	 *	Function Info:
	 *		Input an array, and output whether the middle number is the peak
	 *
	 * 	@param data array
	 * 	@return whether the middle number is a peak
	 */
	public static Boolean findPeakAtMiddle(ArrayList<Integer> dataArray) {

		Boolean result = true;
		
		for (int i = 0; i < dataArray.size(); i++) {
			if (dataArray.get(i) <= dataArray.get(dataArray.size() / 2)) {
			}
			else {
				result = false;
				break;
			}
		}
		
		return result;
	}
}
