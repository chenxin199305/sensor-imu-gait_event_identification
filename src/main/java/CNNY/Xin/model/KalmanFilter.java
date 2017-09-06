package CNNY.Xin.model;

import Jama.Matrix;

public class KalmanFilter {

	private KalmanFilterModel filterModel;
	
	public KalmanFilter() {
	}
	
	public KalmanFilter(KalmanFilterModel kalmanFilterModel) {
		this.filterModel = kalmanFilterModel;
	}
	
	/**
	 *	Function Info:
	 *		do the kalman filter 
	 *
	 *	@param new observe value
	 *	@return new predict value
	 */
	public Matrix filtering(Matrix newObserveValue) {
		
		if (filterModel == null) {
			return null;
		}
		
		Matrix newPredictValue;
		
		filterModel.X_predict = filterModel.A.times(filterModel.X_filtered);
		filterModel.P_predict = filterModel.A.times(filterModel.P).times(filterModel.A.transpose()).plus(
				filterModel.G.times(filterModel.Q).times(filterModel.G.transpose()));
		filterModel.K = filterModel.P_predict.times(
				(filterModel.H.times(filterModel.P_predict).times(filterModel.H.transpose()).plus(filterModel.R)).inverse());	
		filterModel.e = newObserveValue.minus(filterModel.H.times(filterModel.X_predict));
		filterModel.X_filtered = filterModel.X_predict.plus(filterModel.K.times(filterModel.e));
		filterModel.P = filterModel.I.minus(filterModel.K.times(filterModel.H)).times(filterModel.P_predict);
		
		newPredictValue = filterModel.X_filtered;
		
		return newPredictValue;
	}
}
