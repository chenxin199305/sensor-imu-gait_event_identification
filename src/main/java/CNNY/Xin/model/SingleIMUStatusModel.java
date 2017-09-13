package CNNY.Xin.model;

import java.util.ArrayList;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

import Jama.Matrix;
import jssc.SerialPort;

public class SingleIMUStatusModel {

	public SerialPort serialPort;
	public ArrayList<Short> serialPortBufferData = new ArrayList<>();
	public IMUDataDecoder imuDataDecoder = new IMUDataDecoder();
	public IMUFrameDecoder imuFrameDecoder = new IMUFrameDecoder(imuDataDecoder);

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
	private IMUFilteredDataModel imuFilteredDataModel = new IMUFilteredDataModel();

	private KalmanFilter accFilteredXKalmanFilter;
	private KalmanFilter accFilteredYKalmanFilter;
	private KalmanFilter accFilteredZKalmanFilter;

	private KalmanFilter gyoFilteredXKalmanFilter;
	private KalmanFilter gyoFilteredYKalmanFilter;
	private KalmanFilter gyoFilteredZKalmanFilter;

	private KalmanFilter eulerAngleFilteredXKalmanFilter;
	private KalmanFilter eulerAngleFilteredYKalmanFilter;
	private KalmanFilter eulerAngleFilteredZKalmanFilter;
	
	private Integer tempDataArraySize = 15;

	private ArrayList<Integer> accFilteredXTempDataArray = new ArrayList<>();
	private ArrayList<Integer> accFilteredYTempDataArray = new ArrayList<>();
	private ArrayList<Integer> accFilteredZTempDataArray = new ArrayList<>();
	
	private ArrayList<Integer> gyoFilteredXTempDataArray = new ArrayList<>();
	private ArrayList<Integer> gyoFilteredYTempDataArray = new ArrayList<>();
	private ArrayList<Integer> gyoFilteredZTempDataArray = new ArrayList<>();

	private ArrayList<Integer> eulerAngleFilteredXTempDataArray = new ArrayList<>();
	private ArrayList<Integer> eulerAngleFilteredYTempDataArray = new ArrayList<>();
	private ArrayList<Integer> eulerAngleFilteredZTempDataArray = new ArrayList<>();
	
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
	FindToeOffHeelHit findToeOffHeelHit;

	public TimeSeries toeOffHeelHitTimeSeries = new TimeSeries("ToeOffHeelHit");
	
	// Debug
	public TimeSeries debugPPeakTimeSeries = new TimeSeries("debug_pPeak");
	public TimeSeries debugPGradientTimeSeries = new TimeSeries("debug_pGradient");
	public TimeSeries debugPPeriodTimeSeries = new TimeSeries("debug_pPeriod");
	public TimeSeries debugPTotalTimeSeries = new TimeSeries("debug_pTotal");
	
	public SingleIMUStatusModel() {

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
	public void updateTimeSeries() {
		
		final Millisecond millisecond = new Millisecond();

		accRawXAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.AccRaw[0]);
		accRawYAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.AccRaw[1]);
		accRawZAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.AccRaw[2]);

		gyoRawXAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.GyoRaw[0]);
		gyoRawYAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.GyoRaw[1]);
		gyoRawZAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.GyoRaw[2]);

		eulerAnglesXAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.EulerAngles[0]);
		eulerAnglesYAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.EulerAngles[1]);
		eulerAnglesZAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.EulerAngles[2]);
	
		// Filtering Data ...
		imuFilteredDataModel.AccFiltered[0] = imuDataDecoder.imuDataModel.AccRaw[0].intValue();
		imuFilteredDataModel.AccFiltered[1] = imuDataDecoder.imuDataModel.AccRaw[1].intValue();
		imuFilteredDataModel.AccFiltered[2] = imuDataDecoder.imuDataModel.AccRaw[2].intValue();

		imuFilteredDataModel.GyoFiltered[0] = imuDataDecoder.imuDataModel.GyoRaw[0].intValue();
		imuFilteredDataModel.GyoFiltered[1] = imuDataDecoder.imuDataModel.GyoRaw[1].intValue();
		imuFilteredDataModel.GyoFiltered[2] = imuDataDecoder.imuDataModel.GyoRaw[2].intValue();

		imuFilteredDataModel.EulerAnglesFiltered[0] = imuDataDecoder.imuDataModel.EulerAngles[0].intValue();
		imuFilteredDataModel.EulerAnglesFiltered[1] = imuDataDecoder.imuDataModel.EulerAngles[1].intValue();
		imuFilteredDataModel.EulerAnglesFiltered[2] = imuDataDecoder.imuDataModel.EulerAngles[2].intValue();
		
		// 1. Kalman Filter
		if (true) {
			if (gyoFilteredXKalmanFilter == null) {
				double[][] A = {{1}};
				double[][] G = {{1}};
				double[][] H = {{1}};
				double[][] I = {{1}};
				double[][] Q = {{0.01}};
				double[][] R = {{0.01}};
				double[][] P = {{0.01}};
				double[][] X_filtered = {{imuFilteredDataModel.GyoFiltered[0]}};
				
				Matrix aMatrix = new Matrix(A);
				Matrix gMatrix = new Matrix(G);
				Matrix hMatrix = new Matrix(H);
				Matrix iMatrix = new Matrix(I);
				Matrix qMatrix = new Matrix(Q);
				Matrix rMatrix = new Matrix(R);
				Matrix pMatrix = new Matrix(P);
				Matrix xMatrix = new Matrix(X_filtered);
				
				KalmanFilterModel kalmanFilterModel = new KalmanFilterModel();
				kalmanFilterModel.A = aMatrix;
				kalmanFilterModel.G = gMatrix;
				kalmanFilterModel.H = hMatrix;
				kalmanFilterModel.I = iMatrix;
				kalmanFilterModel.Q = qMatrix;
				kalmanFilterModel.R = rMatrix;
				kalmanFilterModel.P = pMatrix;
				kalmanFilterModel.X_filtered = xMatrix;
			
				gyoFilteredXKalmanFilter = new KalmanFilter(kalmanFilterModel);
			}
			if (gyoFilteredYKalmanFilter == null) {
				double[][] A = {{1}};
				double[][] G = {{1}};
				double[][] H = {{1}};
				double[][] I = {{1}};
				double[][] Q = {{0.01}};
				double[][] R = {{0.01}};
				double[][] P = {{0.01}};
				double[][] X_filtered = {{imuFilteredDataModel.GyoFiltered[1]}};
				
				Matrix aMatrix = new Matrix(A);
				Matrix gMatrix = new Matrix(G);
				Matrix hMatrix = new Matrix(H);
				Matrix iMatrix = new Matrix(I);
				Matrix qMatrix = new Matrix(Q);
				Matrix rMatrix = new Matrix(R);
				Matrix pMatrix = new Matrix(P);
				Matrix xMatrix = new Matrix(X_filtered);
				
				KalmanFilterModel kalmanFilterModel = new KalmanFilterModel();
				kalmanFilterModel.A = aMatrix;
				kalmanFilterModel.G = gMatrix;
				kalmanFilterModel.H = hMatrix;
				kalmanFilterModel.I = iMatrix;
				kalmanFilterModel.Q = qMatrix;
				kalmanFilterModel.R = rMatrix;
				kalmanFilterModel.P = pMatrix;
				kalmanFilterModel.X_filtered = xMatrix;
			
				gyoFilteredYKalmanFilter = new KalmanFilter(kalmanFilterModel);
			}
			if (gyoFilteredZKalmanFilter == null) {
				double[][] A = {{1}};
				double[][] G = {{1}};
				double[][] H = {{1}};
				double[][] I = {{1}};
				double[][] Q = {{0.01}};
				double[][] R = {{0.01}};
				double[][] P = {{0.01}};
				double[][] X_filtered = {{imuFilteredDataModel.GyoFiltered[2]}};
				
				Matrix aMatrix = new Matrix(A);
				Matrix gMatrix = new Matrix(G);
				Matrix hMatrix = new Matrix(H);
				Matrix iMatrix = new Matrix(I);
				Matrix qMatrix = new Matrix(Q);
				Matrix rMatrix = new Matrix(R);
				Matrix pMatrix = new Matrix(P);
				Matrix xMatrix = new Matrix(X_filtered);
				
				KalmanFilterModel kalmanFilterModel = new KalmanFilterModel();
				kalmanFilterModel.A = aMatrix;
				kalmanFilterModel.G = gMatrix;
				kalmanFilterModel.H = hMatrix;
				kalmanFilterModel.I = iMatrix;
				kalmanFilterModel.Q = qMatrix;
				kalmanFilterModel.R = rMatrix;
				kalmanFilterModel.P = pMatrix;
				kalmanFilterModel.X_filtered = xMatrix;
			
				gyoFilteredZKalmanFilter = new KalmanFilter(kalmanFilterModel);
			}
			
			imuFilteredDataModel.GyoFiltered[0] = (int) gyoFilteredXKalmanFilter.filtering(
					new Matrix(new double[][] {{imuFilteredDataModel.GyoFiltered[0]}})).get(0, 0);
			imuFilteredDataModel.GyoFiltered[1] = (int) gyoFilteredYKalmanFilter.filtering(
					new Matrix(new double[][] {{imuFilteredDataModel.GyoFiltered[1]}})).get(0, 0);
			imuFilteredDataModel.GyoFiltered[2] = (int) gyoFilteredZKalmanFilter.filtering(
					new Matrix(new double[][] {{imuFilteredDataModel.GyoFiltered[2]}})).get(0, 0);
		}
		
		// 2. Average Filter
		if (false) {
			while (accFilteredXTempDataArray.size() >= tempDataArraySize) {
				accFilteredXTempDataArray.remove(0);
			}
			while (accFilteredYTempDataArray.size() >= tempDataArraySize) {
				accFilteredYTempDataArray.remove(0);
			}
			while (accFilteredZTempDataArray.size() >= tempDataArraySize) {
				accFilteredZTempDataArray.remove(0);
			}
			while (gyoFilteredXTempDataArray.size() >= tempDataArraySize) {
				gyoFilteredXTempDataArray.remove(0);
			}
			while (gyoFilteredYTempDataArray.size() >= tempDataArraySize) {
				gyoFilteredYTempDataArray.remove(0);
			}
			while (gyoFilteredZTempDataArray.size() >= tempDataArraySize) {
				gyoFilteredZTempDataArray.remove(0);
			}
			while (eulerAngleFilteredXTempDataArray.size() >= tempDataArraySize) {
				eulerAngleFilteredXTempDataArray.remove(0);
			}
			while (eulerAngleFilteredYTempDataArray.size() >= tempDataArraySize) {
				eulerAngleFilteredYTempDataArray.remove(0);
			}
			while (eulerAngleFilteredZTempDataArray.size() >= tempDataArraySize) {
				eulerAngleFilteredZTempDataArray.remove(0);
			}

			accFilteredXTempDataArray.add(imuFilteredDataModel.AccFiltered[0]);
			accFilteredYTempDataArray.add(imuFilteredDataModel.AccFiltered[1]);
			accFilteredZTempDataArray.add(imuFilteredDataModel.AccFiltered[2]);

			gyoFilteredXTempDataArray.add(imuFilteredDataModel.GyoFiltered[0]);
			gyoFilteredYTempDataArray.add(imuFilteredDataModel.GyoFiltered[1]);
			gyoFilteredZTempDataArray.add(imuFilteredDataModel.GyoFiltered[2]);

			eulerAngleFilteredXTempDataArray.add(imuFilteredDataModel.EulerAnglesFiltered[0]);
			eulerAngleFilteredYTempDataArray.add(imuFilteredDataModel.EulerAnglesFiltered[1]);
			eulerAngleFilteredZTempDataArray.add(imuFilteredDataModel.EulerAnglesFiltered[2]);

			Integer accFilteredXTempSum = 0;
			Integer accFilteredYTempSum = 0;
			Integer accFilteredZTempSum = 0;

			Integer gyoFilteredXTempSum = 0;
			Integer gyoFilteredYTempSum = 0;
			Integer gyoFilteredZTempSum = 0;

			Integer eulerAngleFilteredXTempSum = 0;
			Integer eulerAngleFilteredYTempSum = 0;
			Integer eulerAngleFilteredZTempSum = 0;
			
			for (int i = 0; i < accFilteredXTempDataArray.size(); i++) {
				accFilteredXTempSum += accFilteredXTempDataArray.get(i);
			}
			for (int i = 0; i < accFilteredYTempDataArray.size(); i++) {
				accFilteredYTempSum += accFilteredYTempDataArray.get(i);
			}
			for (int i = 0; i < accFilteredZTempDataArray.size(); i++) {
				accFilteredZTempSum += accFilteredZTempDataArray.get(i);
			}

			for (int i = 0; i < gyoFilteredXTempDataArray.size(); i++) {
				gyoFilteredXTempSum += gyoFilteredXTempDataArray.get(i);
			}
			for (int i = 0; i < gyoFilteredYTempDataArray.size(); i++) {
				gyoFilteredYTempSum += gyoFilteredYTempDataArray.get(i);
			}
			for (int i = 0; i < gyoFilteredZTempDataArray.size(); i++) {
				gyoFilteredZTempSum += gyoFilteredZTempDataArray.get(i);
			}
			
			for (int i = 0; i < eulerAngleFilteredXTempDataArray.size(); i++) {
				eulerAngleFilteredXTempSum += eulerAngleFilteredXTempDataArray.get(i);
			}
			for (int i = 0; i < eulerAngleFilteredYTempDataArray.size(); i++) {
				eulerAngleFilteredYTempSum += eulerAngleFilteredYTempDataArray.get(i);
			}
			for (int i = 0; i < eulerAngleFilteredZTempDataArray.size(); i++) {
				eulerAngleFilteredZTempSum += eulerAngleFilteredZTempDataArray.get(i);
			}
			
			imuFilteredDataModel.AccFiltered[0] = accFilteredXTempSum / accFilteredXTempDataArray.size();
			imuFilteredDataModel.AccFiltered[1] = accFilteredYTempSum / accFilteredYTempDataArray.size();
			imuFilteredDataModel.AccFiltered[2] = accFilteredZTempSum / accFilteredZTempDataArray.size();
			
			imuFilteredDataModel.GyoFiltered[0] = gyoFilteredXTempSum / gyoFilteredXTempDataArray.size();
			imuFilteredDataModel.GyoFiltered[1] = gyoFilteredYTempSum / gyoFilteredYTempDataArray.size();
			imuFilteredDataModel.GyoFiltered[2] = gyoFilteredZTempSum / gyoFilteredZTempDataArray.size();

			imuFilteredDataModel.EulerAnglesFiltered[0] = eulerAngleFilteredXTempSum / eulerAngleFilteredXTempDataArray.size();
			imuFilteredDataModel.EulerAnglesFiltered[1] = eulerAngleFilteredYTempSum / eulerAngleFilteredXTempDataArray.size();
			imuFilteredDataModel.EulerAnglesFiltered[2] = eulerAngleFilteredZTempSum / eulerAngleFilteredXTempDataArray.size();
		}
		
		// 99. add to time series
		accFilteredXAxisTimeSeries.addOrUpdate(millisecond, imuFilteredDataModel.AccFiltered[0]);
		accFilteredYAxisTimeSeries.addOrUpdate(millisecond, imuFilteredDataModel.AccFiltered[1]);
		accFilteredZAxisTimeSeries.addOrUpdate(millisecond, imuFilteredDataModel.AccFiltered[2]);

		gyoFilteredXAxisTimeSeries.addOrUpdate(millisecond, imuFilteredDataModel.GyoFiltered[0]);
		gyoFilteredYAxisTimeSeries.addOrUpdate(millisecond, imuFilteredDataModel.GyoFiltered[1]);
		gyoFilteredZAxisTimeSeries.addOrUpdate(millisecond, imuFilteredDataModel.GyoFiltered[2]);
		
		eulerAnglesFilteredXAxisTimeSeries.addOrUpdate(millisecond, imuFilteredDataModel.EulerAnglesFiltered[0]);
		eulerAnglesFilteredYAxisTimeSeries.addOrUpdate(millisecond, imuFilteredDataModel.EulerAnglesFiltered[1]);
		eulerAnglesFilteredZAxisTimeSeries.addOrUpdate(millisecond, imuFilteredDataModel.EulerAnglesFiltered[2]);
	
		//----------------------------------------------------------
		// find toe-off and heel hit
		if (true) {
			if (findToeOffHeelHit == null) {
//				FindToeOffHeelHitModel findToeOffHeelHitModel = new FindToeOffHeelHitModel();
//				findToeOffHeelHitModel.windowWidth = 10;
//				findToeOffHeelHitModel.gradientLimitation = 15;
				
				findToeOffHeelHit = new FindToeOffHeelHit();
				findToeOffHeelHit.findToeOffHeelHitModel.windowWidth = 6;
			}
		
			if (gyoFilteredYAxisTimeSeries.getItemCount() >= findToeOffHeelHit.findToeOffHeelHitModel.windowWidth) {
				ArrayList<Integer> tempArray = new ArrayList<>();
				
				for (int i = gyoFilteredYAxisTimeSeries.getItemCount() - findToeOffHeelHit.findToeOffHeelHitModel.windowWidth;
						i < gyoFilteredYAxisTimeSeries.getItemCount();
						i++) {
					tempArray.add(gyoFilteredYAxisTimeSeries.getValue(i).intValue());
				}
				
				Boolean findResult = findToeOffHeelHit.isToeOffOrHeelHitAtMiddle(tempArray);

				debugPPeakTimeSeries.addOrUpdate(millisecond, findToeOffHeelHit.findToeOffHeelHitModel.pPeak);
				debugPGradientTimeSeries.addOrUpdate(millisecond, findToeOffHeelHit.findToeOffHeelHitModel.pGradient);
				debugPPeriodTimeSeries.addOrUpdate(millisecond, findToeOffHeelHit.findToeOffHeelHitModel.pPeriod);
				debugPTotalTimeSeries.addOrUpdate(millisecond, findToeOffHeelHit.findToeOffHeelHitModel.pTotal);
				
				if(findResult) {
					
					toeOffHeelHitTimeSeries.addOrUpdate(
							gyoFilteredYAxisTimeSeries.getTimePeriod(gyoFilteredYAxisTimeSeries.getItemCount() - tempArray.size() / 2),
							gyoFilteredYAxisTimeSeries.getValue(gyoFilteredYAxisTimeSeries.getItemCount() - tempArray.size() / 2));
				}
				else {
//					toeOffHeelHitTimeSeries.addOrUpdate(millisecond, 0);
				}
			}
		}
	}

}
