package CNNY.Xin.model;

import java.util.ArrayList;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

import Jama.Matrix;

public class SingleIMUDataDisplayModel {

	// char panel data
	public TimeSeries accRawXAxisTimeSeries = new TimeSeries("accRawX");
	public TimeSeries accRawYAxisTimeSeries = new TimeSeries("accRawY");
	public TimeSeries accRawZAxisTimeSeries = new TimeSeries("accRawZ");

	public TimeSeries gyoRawXAxisTimeSeries = new TimeSeries("gyoRawX");
	public TimeSeries gyoRawYAxisTimeSeries = new TimeSeries("gyoRawY");
	public TimeSeries gyoRawZAxisTimeSeries = new TimeSeries("gyoRawZ");

	public TimeSeries magRawXAxisTimeSeries = new TimeSeries("magRawX");
	public TimeSeries magRawYAxisTimeSeries = new TimeSeries("magRawY");
	public TimeSeries magRawZAxisTimeSeries = new TimeSeries("magRawZ");

	public TimeSeries eulerAnglesXAxisTimeSeries = new TimeSeries("eulerX");
	public TimeSeries eulerAnglesYAxisTimeSeries = new TimeSeries("eulerY");
	public TimeSeries eulerAnglesZAxisTimeSeries = new TimeSeries("eulerZ");

	public TimeSeries pressureAxisTimeSeries = new TimeSeries("pressure");
	public TimeSeries temperatureAxisTimeSeries = new TimeSeries("temperature");

	// Filtered Data
	public IMUFilteredDataModel imuFilteredDataModel = new IMUFilteredDataModel();

	public KalmanFilter accFilteredXKalmanFilter;
	public KalmanFilter accFilteredYKalmanFilter;
	public KalmanFilter accFilteredZKalmanFilter;

	public KalmanFilter gyoFilteredXKalmanFilter;
	public KalmanFilter gyoFilteredYKalmanFilter;
	public KalmanFilter gyoFilteredZKalmanFilter;

	public KalmanFilter eulerAngleFilteredXKalmanFilter;
	public KalmanFilter eulerAngleFilteredYKalmanFilter;
	public KalmanFilter eulerAngleFilteredZKalmanFilter;

	public Integer tempDataArraySize = 15;

	public ArrayList<Integer> accFilteredXTempDataArray = new ArrayList<>();
	public ArrayList<Integer> accFilteredYTempDataArray = new ArrayList<>();
	public ArrayList<Integer> accFilteredZTempDataArray = new ArrayList<>();

	public ArrayList<Integer> gyoFilteredXTempDataArray = new ArrayList<>();
	public ArrayList<Integer> gyoFilteredYTempDataArray = new ArrayList<>();
	public ArrayList<Integer> gyoFilteredZTempDataArray = new ArrayList<>();

	public ArrayList<Integer> eulerAngleFilteredXTempDataArray = new ArrayList<>();
	public ArrayList<Integer> eulerAngleFilteredYTempDataArray = new ArrayList<>();
	public ArrayList<Integer> eulerAngleFilteredZTempDataArray = new ArrayList<>();

	public TimeSeries accFilteredXAxisTimeSeries = new TimeSeries("accFilteredX");
	public TimeSeries accFilteredYAxisTimeSeries = new TimeSeries("accFilteredY");
	public TimeSeries accFilteredZAxisTimeSeries = new TimeSeries("accFilteredZ");

	public TimeSeries gyoFilteredXAxisTimeSeries = new TimeSeries("gyoFilteredX");
	public TimeSeries gyoFilteredYAxisTimeSeries = new TimeSeries("gyoFilteredY");
	public TimeSeries gyoFilteredZAxisTimeSeries = new TimeSeries("gyoFilteredZ");

	public TimeSeries eulerAnglesFilteredXAxisTimeSeries = new TimeSeries("eulerAngleFilteredX");
	public TimeSeries eulerAnglesFilteredYAxisTimeSeries = new TimeSeries("eulerAngleFilteredY");
	public TimeSeries eulerAnglesFilteredZAxisTimeSeries = new TimeSeries("eulerAngleFilteredZ");

	// Find Toe Off or Heel Hit
	public FindToeOffHeelHit findToeOffHeelHit;

	public TimeSeries toeOffHeelHitTimeSeries = new TimeSeries("ToeOffHeelHit");

	// Debug
	public TimeSeries debugPPeakTimeSeries = new TimeSeries("debug_pPeak");
	public TimeSeries debugPGradientTimeSeries = new TimeSeries("debug_pGradient");
	public TimeSeries debugPPeriodTimeSeries = new TimeSeries("debug_pPeriod");
	public TimeSeries debugPTotalTimeSeries = new TimeSeries("debug_pTotal");

	public SingleIMUDataDisplayModel() {

		long timeForRecord = 6 * 1000;

		accRawXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accRawYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accRawZAxisTimeSeries.setMaximumItemAge(timeForRecord);

		gyoRawXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoRawYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoRawZAxisTimeSeries.setMaximumItemAge(timeForRecord);

		eulerAnglesXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		eulerAnglesYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		eulerAnglesZAxisTimeSeries.setMaximumItemAge(timeForRecord);

		// Filtered Data
		accFilteredXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accFilteredYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accFilteredZAxisTimeSeries.setMaximumItemAge(timeForRecord);

		gyoFilteredXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoFilteredYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoFilteredZAxisTimeSeries.setMaximumItemAge(timeForRecord);

		eulerAnglesFilteredXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		eulerAnglesFilteredYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		eulerAnglesFilteredZAxisTimeSeries.setMaximumItemAge(timeForRecord);

		// Toe Off or Heel Hit
		toeOffHeelHitTimeSeries.setMaximumItemAge(timeForRecord);

		// debug
		debugPPeakTimeSeries.setMaximumItemAge(timeForRecord);
		debugPGradientTimeSeries.setMaximumItemAge(timeForRecord);
		debugPPeriodTimeSeries.setMaximumItemAge(timeForRecord);
		debugPTotalTimeSeries.setMaximumItemAge(timeForRecord);

	}
	
	/**
	 *	Function Info:
	 *		update time series 
	 */
	public void updateTimeSeries(IMUDataDecoder imuDataDecoder) {

		
	}
}
