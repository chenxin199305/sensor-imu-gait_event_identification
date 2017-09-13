package CNNY.Xin.model;

import java.util.ArrayList;

public class FindToeOffHeelHit {

	public FindToeOffHeelHitModel findToeOffHeelHitModel;
	
	public FindToeOffHeelHit() {
		if (findToeOffHeelHitModel == null) {
			findToeOffHeelHitModel = new FindToeOffHeelHitModel();
		}
	}

	/**
	 *	Function Info:
	 *		This function find whether it is a toe-off or heel-hit at the middle of the array 
	 */
	public Boolean findToeOffHeelHitAtMiddle(ArrayList<Integer> dataArray) {

		if (!FindPeak.findPeakAtMiddle(dataArray)) {
			return false;
		}
		
		Integer foreGradient = (dataArray.get(dataArray.size() / 2) - dataArray.get(0)) / (dataArray.size() / 2);
		Integer backGradient = (dataArray.get(dataArray.size() / 2) - dataArray.get(dataArray.size() - 1)) / (dataArray.size() / 2);

		if (foreGradient <= findToeOffHeelHitModel.gradientLimitation 
				|| backGradient <= findToeOffHeelHitModel.gradientLimitation) {
			return false;
		}
		
		return true;
	}
	
	/**
	 *	Function Info:
	 *		This function find whether it is a toe-off or heel-hit at the midlle of the data array,
	 *		using a method depending on probabilities.s 
	 */
	public Boolean isToeOffOrHeelHitAtMiddle(ArrayList<Integer> dataArray) {
		
		// 1. Peak?
		if (FindPeak.findPeakAtMiddle(dataArray)) {
			findToeOffHeelHitModel.pPeak = 1.0;
		}
		else {
			findToeOffHeelHitModel.pPeak = 0.0;
		}
		
		// 2. Gradient?
		Double foreGradient = (double)(dataArray.get(dataArray.size() / 2) - dataArray.get(0)) / (double)(dataArray.size() / 2);
		Double backGradient = (double)(dataArray.get(dataArray.size() / 2) - dataArray.get(dataArray.size() - 1)) / (double)(dataArray.size() / 2);
		Double totalGradient = (foreGradient + backGradient) / 2.0;
		
		findToeOffHeelHitModel.pGradient = GaussianDistribution.getDistributionValue(totalGradient, 150.0, 40.0);
		findToeOffHeelHitModel.pGradient = findToeOffHeelHitModel.pGradient * 100.0;
		
		// 3. Period?
		findToeOffHeelHitModel.distanceToLastTOHHCount++;
		
		findToeOffHeelHitModel.pPeriod = GaussianDistribution.getDistributionValue(findToeOffHeelHitModel.distanceToLastTOHHCount.doubleValue(), 55.0, 5.0)
				+ GaussianDistribution.getDistributionValue(findToeOffHeelHitModel.distanceToLastTOHHCount.doubleValue(), 100.0, 5.0);
		findToeOffHeelHitModel.pPeriod = findToeOffHeelHitModel.pPeriod * 100;
		
		// Find result
		findToeOffHeelHitModel.pTotal = findToeOffHeelHitModel.pPeak * findToeOffHeelHitModel.pGradient ;//* findToeOffHeelHitModel.pPeriod;

		if (findToeOffHeelHitModel.pTotal > 0.5) {
			findToeOffHeelHitModel.distanceToLastTOHHCount = 0;
			return true;
		}
		else {
			if (findToeOffHeelHitModel.distanceToLastTOHHCount > findToeOffHeelHitModel.distanceToLastTOHHCountLimitation) {
				findToeOffHeelHitModel.distanceToLastTOHHCount = 0;
			}
			return false;
		}
	}
}
