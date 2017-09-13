package CNNY.Xin.model;

public class GaussianDistribution {

	GaussianDistributionModel gaussianDistributionModel;

	/**
	 *	Func Info:
	 *		Gaussian Distribution Initialization 
	 */
	public GaussianDistribution(Double mu, Double delta) {
		gaussianDistributionModel.mu 	= mu;
		gaussianDistributionModel.delta = delta;
	}

	public Double getDistributionValue(Double x) {
		
		Double y = (1.0 / Math.sqrt(2 * Math.PI * Math.pow(gaussianDistributionModel.delta, 2))) 
				* Math.exp(-Math.pow((x - gaussianDistributionModel.mu), 2) / (2 * Math.pow(gaussianDistributionModel.delta, 2)));
		
		return y;
	}

	public static Double getDistributionValue(Double x, Double mu, Double delta) {
		
		Double y = (1.0 / Math.sqrt(2 * Math.PI * Math.pow(delta, 2))) 
				* Math.exp(-Math.pow((x - mu), 2) / (2 * Math.pow(delta, 2)));
		
		return y;
	}
}
