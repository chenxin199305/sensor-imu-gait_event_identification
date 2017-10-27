package CNNY.Xin.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFileChooser;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;

import CNNY.Xin.model.FindToeOffHeelHit;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.IMUTOHSDataModel;
import CNNY.Xin.model.KalmanFilter;
import CNNY.Xin.model.KalmanFilterModel;
import CNNY.Xin.model.IMUDataDisplayModel;
import CNNY.Xin.view.IMUDataDisplayPanel;
import Jama.Matrix;

public class IMUDataDisplayAction {

	private IMUDataDisplayModel model;
	private IMUDataDisplayPanel panel;
	
	private Boolean recordingFlag = true;
	
	public IMUDataDisplayAction(
			IMUDataDisplayModel iMUDataDisplayModel,
			IMUDataDisplayPanel singleIMUDataDisplayPanel) {
	
		this.model = iMUDataDisplayModel;
		this.panel = singleIMUDataDisplayPanel;
	}
	
	/**
	 *	Func Info:
	 *		imu data update event
	 */
	public void imuDataUpdate(IMUDataModel imuDataModel) {

		if (recordingFlag == true) {
		}
		else {
			return;
		}
		
		try {
			final RegularTimePeriod currentTime = imuDataModel.receiveTime;

//			model.accRawXAxisTimeSeries.addOrUpdate(currentTime, imuDataModel.AccRaw[0]);
//			model.accRawYAxisTimeSeries.addOrUpdate(currentTime, imuDataModel.AccRaw[1]);
//			model.accRawZAxisTimeSeries.addOrUpdate(currentTime, imuDataModel.AccRaw[2]);

			model.gyoRawXAxisTimeSeries.addOrUpdate(currentTime, imuDataModel.GyoRaw[0] / 10.0);
			model.gyoRawYAxisTimeSeries.addOrUpdate(currentTime, imuDataModel.GyoRaw[1] / 10.0);
			model.gyoRawZAxisTimeSeries.addOrUpdate(currentTime, imuDataModel.GyoRaw[2] / 10.0);

//			model.eulerAnglesXAxisTimeSeries.addOrUpdate(currentTime, imuDataModel.EulerAngles[0]);
//			model.eulerAnglesYAxisTimeSeries.addOrUpdate(currentTime, imuDataModel.EulerAngles[1]);
//			model.eulerAnglesZAxisTimeSeries.addOrUpdate(currentTime, imuDataModel.EulerAngles[2]);

			// Filtering Data ...
//			model.imuFilteredDataModel.AccFiltered[0] = imuDataModel.AccRaw[0].intValue();
//			model.imuFilteredDataModel.AccFiltered[1] = imuDataModel.AccRaw[1].intValue();
//			model.imuFilteredDataModel.AccFiltered[2] = imuDataModel.AccRaw[2].intValue();

			model.imuFilteredDataModel.GyoFiltered[0] = imuDataModel.GyoRaw[0].intValue() / 10;
			model.imuFilteredDataModel.GyoFiltered[1] = imuDataModel.GyoRaw[1].intValue() / 10;
			model.imuFilteredDataModel.GyoFiltered[2] = imuDataModel.GyoRaw[2].intValue() / 10;

//			model.imuFilteredDataModel.EulerAnglesFiltered[0] = imuDataModel.EulerAngles[0].intValue();
//			model.imuFilteredDataModel.EulerAnglesFiltered[1] = imuDataModel.EulerAngles[1].intValue();
//			model.imuFilteredDataModel.EulerAnglesFiltered[2] = imuDataModel.EulerAngles[2].intValue();

			// 1. Kalman Filter
			if (false) {
				if (model.gyoFilteredXKalmanFilter == null) {
					double[][] A = {{1}};
					double[][] G = {{1}};
					double[][] H = {{1}};
					double[][] I = {{1}};
					double[][] Q = {{0.01}};
					double[][] R = {{0.01}};
					double[][] P = {{0.01}};
					double[][] X_filtered = {{model.imuFilteredDataModel.GyoFiltered[0]}};

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

					model.gyoFilteredXKalmanFilter = new KalmanFilter(kalmanFilterModel);
				}
				if (model.gyoFilteredYKalmanFilter == null) {
					double[][] A = {{1}};
					double[][] G = {{1}};
					double[][] H = {{1}};
					double[][] I = {{1}};
					double[][] Q = {{0.01}};
					double[][] R = {{0.01}};
					double[][] P = {{0.01}};
					double[][] X_filtered = {{model.imuFilteredDataModel.GyoFiltered[1]}};

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

					model.gyoFilteredYKalmanFilter = new KalmanFilter(kalmanFilterModel);
				}
				if (model.gyoFilteredZKalmanFilter == null) {
					double[][] A = {{1}};
					double[][] G = {{1}};
					double[][] H = {{1}};
					double[][] I = {{1}};
					double[][] Q = {{0.01}};
					double[][] R = {{0.01}};
					double[][] P = {{0.01}};
					double[][] X_filtered = {{model.imuFilteredDataModel.GyoFiltered[2]}};

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

					model.gyoFilteredZKalmanFilter = new KalmanFilter(kalmanFilterModel);
				}

				model.imuFilteredDataModel.GyoFiltered[0] = (int) model.gyoFilteredXKalmanFilter.filtering(
						new Matrix(new double[][] {{model.imuFilteredDataModel.GyoFiltered[0]}})).get(0, 0);
				model.imuFilteredDataModel.GyoFiltered[1] = (int) model.gyoFilteredYKalmanFilter.filtering(
						new Matrix(new double[][] {{model.imuFilteredDataModel.GyoFiltered[1]}})).get(0, 0);
				model.imuFilteredDataModel.GyoFiltered[2] = (int) model.gyoFilteredZKalmanFilter.filtering(
						new Matrix(new double[][] {{model.imuFilteredDataModel.GyoFiltered[2]}})).get(0, 0);
			}

			// 2. Average Filter
			if (true) {
//				while (model.accFilteredXTempDataArray.size() >= model.tempDataArraySize) {
//					model.accFilteredXTempDataArray.remove(0);
//				}
//				while (model.accFilteredYTempDataArray.size() >= model.tempDataArraySize) {
//					model.accFilteredYTempDataArray.remove(0);
//				}
//				while (model.accFilteredZTempDataArray.size() >= model.tempDataArraySize) {
//					model.accFilteredZTempDataArray.remove(0);
//				}
				
				while (model.gyoFilteredXTempDataArray.size() >= model.tempDataArraySize) {
					model.gyoFilteredXTempDataArray.remove(0);
				}
				while (model.gyoFilteredYTempDataArray.size() >= model.tempDataArraySize) {
					model.gyoFilteredYTempDataArray.remove(0);
				}
				while (model.gyoFilteredZTempDataArray.size() >= model.tempDataArraySize) {
					model.gyoFilteredZTempDataArray.remove(0);
				}
				
//				while (model.eulerAngleFilteredXTempDataArray.size() >= model.tempDataArraySize) {
//					model.eulerAngleFilteredXTempDataArray.remove(0);
//				}
//				while (model.eulerAngleFilteredYTempDataArray.size() >= model.tempDataArraySize) {
//					model.eulerAngleFilteredYTempDataArray.remove(0);
//				}
//				while (model.eulerAngleFilteredZTempDataArray.size() >= model.tempDataArraySize) {
//					model.eulerAngleFilteredZTempDataArray.remove(0);
//				}

//				model.accFilteredXTempDataArray.add(model.imuFilteredDataModel.AccFiltered[0]);
//				model.accFilteredYTempDataArray.add(model.imuFilteredDataModel.AccFiltered[1]);
//				model.accFilteredZTempDataArray.add(model.imuFilteredDataModel.AccFiltered[2]);

				model.gyoFilteredXTempDataArray.add(model.imuFilteredDataModel.GyoFiltered[0]);
				model.gyoFilteredYTempDataArray.add(model.imuFilteredDataModel.GyoFiltered[1]);
				model.gyoFilteredZTempDataArray.add(model.imuFilteredDataModel.GyoFiltered[2]);

//				model.eulerAngleFilteredXTempDataArray.add(model.imuFilteredDataModel.EulerAnglesFiltered[0]);
//				model.eulerAngleFilteredYTempDataArray.add(model.imuFilteredDataModel.EulerAnglesFiltered[1]);
//				model.eulerAngleFilteredZTempDataArray.add(model.imuFilteredDataModel.EulerAnglesFiltered[2]);

//				Integer accFilteredXTempSum = 0;
//				Integer accFilteredYTempSum = 0;
//				Integer accFilteredZTempSum = 0;

				Integer gyoFilteredXTempSum = 0;
				Integer gyoFilteredYTempSum = 0;
				Integer gyoFilteredZTempSum = 0;

//				Integer eulerAngleFilteredXTempSum = 0;
//				Integer eulerAngleFilteredYTempSum = 0;
//				Integer eulerAngleFilteredZTempSum = 0;

//				for (int i = 0; i < model.accFilteredXTempDataArray.size(); i++) {
//					accFilteredXTempSum += model.accFilteredXTempDataArray.get(i);
//				}
//				for (int i = 0; i < model.accFilteredYTempDataArray.size(); i++) {
//					accFilteredYTempSum += model.accFilteredYTempDataArray.get(i);
//				}
//				for (int i = 0; i < model.accFilteredZTempDataArray.size(); i++) {
//					accFilteredZTempSum += model.accFilteredZTempDataArray.get(i);
//				}

				for (int i = 0; i < model.gyoFilteredXTempDataArray.size(); i++) {
					gyoFilteredXTempSum += model.gyoFilteredXTempDataArray.get(i);
				}
				for (int i = 0; i < model.gyoFilteredYTempDataArray.size(); i++) {
					gyoFilteredYTempSum += model.gyoFilteredYTempDataArray.get(i);
				}
				for (int i = 0; i < model.gyoFilteredZTempDataArray.size(); i++) {
					gyoFilteredZTempSum += model.gyoFilteredZTempDataArray.get(i);
				}

//				for (int i = 0; i < model.eulerAngleFilteredXTempDataArray.size(); i++) {
//					eulerAngleFilteredXTempSum += model.eulerAngleFilteredXTempDataArray.get(i);
//				}
//				for (int i = 0; i < model.eulerAngleFilteredYTempDataArray.size(); i++) {
//					eulerAngleFilteredYTempSum += model.eulerAngleFilteredYTempDataArray.get(i);
//				}
//				for (int i = 0; i < model.eulerAngleFilteredZTempDataArray.size(); i++) {
//					eulerAngleFilteredZTempSum += model.eulerAngleFilteredZTempDataArray.get(i);
//				}

				model.imuFilteredDataModel.GyoFiltered[0] = gyoFilteredXTempSum / model.gyoFilteredXTempDataArray.size();
				model.imuFilteredDataModel.GyoFiltered[1] = gyoFilteredYTempSum / model.gyoFilteredYTempDataArray.size();
				model.imuFilteredDataModel.GyoFiltered[2] = gyoFilteredZTempSum / model.gyoFilteredZTempDataArray.size();

//				model.imuFilteredDataModel.EulerAnglesFiltered[0] = eulerAngleFilteredXTempSum / model.eulerAngleFilteredXTempDataArray.size();
//				model.imuFilteredDataModel.EulerAnglesFiltered[1] = eulerAngleFilteredYTempSum / model.eulerAngleFilteredXTempDataArray.size();
//				model.imuFilteredDataModel.EulerAnglesFiltered[2] = eulerAngleFilteredZTempSum / model.eulerAngleFilteredXTempDataArray.size();
			}

			// 99. add to time series
			model.accFilteredXAxisTimeSeries.addOrUpdate(currentTime, model.imuFilteredDataModel.AccFiltered[0]);
			model.accFilteredYAxisTimeSeries.addOrUpdate(currentTime, model.imuFilteredDataModel.AccFiltered[1]);
			model.accFilteredZAxisTimeSeries.addOrUpdate(currentTime, model.imuFilteredDataModel.AccFiltered[2]);

			model.gyoFilteredXAxisTimeSeries.addOrUpdate(currentTime, model.imuFilteredDataModel.GyoFiltered[0]);
			model.gyoFilteredYAxisTimeSeries.addOrUpdate(currentTime, model.imuFilteredDataModel.GyoFiltered[1]);
			model.gyoFilteredZAxisTimeSeries.addOrUpdate(currentTime, model.imuFilteredDataModel.GyoFiltered[2]);

			model.eulerAnglesFilteredXAxisTimeSeries.addOrUpdate(currentTime, model.imuFilteredDataModel.EulerAnglesFiltered[0]);
			model.eulerAnglesFilteredYAxisTimeSeries.addOrUpdate(currentTime, model.imuFilteredDataModel.EulerAnglesFiltered[1]);
			model.eulerAnglesFilteredZAxisTimeSeries.addOrUpdate(currentTime, model.imuFilteredDataModel.EulerAnglesFiltered[2]);

			//----------------------------------------------------------
			// find toe-off and heel hit
			if (false) {
				if (model.findToeOffHeelHit == null) {
					//				FindToeOffHeelHitModel findToeOffHeelHitModel = new FindToeOffHeelHitModel();
					//				findToeOffHeelHitModel.windowWidth = 10;
					//				findToeOffHeelHitModel.gradientLimitation = 15;

					model.findToeOffHeelHit = new FindToeOffHeelHit();
					model.findToeOffHeelHit.findToeOffHeelHitModel.windowWidth = 6;
				}

				if (model.gyoFilteredYAxisTimeSeries.getItemCount() >= model.findToeOffHeelHit.findToeOffHeelHitModel.windowWidth) {
					ArrayList<Integer> tempArray = new ArrayList<>();

					for (int i = model.gyoFilteredYAxisTimeSeries.getItemCount() - model.findToeOffHeelHit.findToeOffHeelHitModel.windowWidth;
							i < model.gyoFilteredYAxisTimeSeries.getItemCount();
							i++) {
						tempArray.add(model.gyoFilteredYAxisTimeSeries.getValue(i).intValue());
					}

					Boolean findResult = model.findToeOffHeelHit.isToeOffOrHeelHitAtMiddle(tempArray);

					model.debugPPeakTimeSeries.addOrUpdate(currentTime, model.findToeOffHeelHit.findToeOffHeelHitModel.pPeak);
					model.debugPGradientTimeSeries.addOrUpdate(currentTime, model.findToeOffHeelHit.findToeOffHeelHitModel.pGradient);
					model.debugPPeriodTimeSeries.addOrUpdate(currentTime, model.findToeOffHeelHit.findToeOffHeelHitModel.pPeriod);
					model.debugPTotalTimeSeries.addOrUpdate(currentTime, model.findToeOffHeelHit.findToeOffHeelHitModel.pTotal);

					if(findResult) {
						model.toeOffHeelHitTimeSeries.addOrUpdate(
								model.gyoFilteredYAxisTimeSeries.getTimePeriod(model.gyoFilteredYAxisTimeSeries.getItemCount() - tempArray.size() / 2),
								model.gyoFilteredYAxisTimeSeries.getValue(model.gyoFilteredYAxisTimeSeries.getItemCount() - tempArray.size() / 2));
					}
					else {
						//					toeOffHeelHitTimeSeries.addOrUpdate(millisecond, 0);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("1");
		}
	}
	
	/**
	 *	Function Info
	 *		acceleration check box state change
	 */
	public void accelerationCheckBoxStateChange() {
		if (panel.checkBoxAcceleration.isSelected()) {
			panel.chartDataSet.addSeries(model.accRawXAxisTimeSeries);
			panel.chartDataSet.addSeries(model.accRawYAxisTimeSeries);
			panel.chartDataSet.addSeries(model.accRawZAxisTimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.accRawXAxisTimeSeries);
			panel.chartDataSet.removeSeries(model.accRawYAxisTimeSeries);
			panel.chartDataSet.removeSeries(model.accRawZAxisTimeSeries);

			model.accRawXAxisTimeSeries.clear();
			model.accRawYAxisTimeSeries.clear();
			model.accRawZAxisTimeSeries.clear();
		}
	}

	/**
	 *	Function Info
	 *		angle velocity check box state change
	 */
	public void angleVelocityCheckBoxStateChange() {
		if (panel.checkBoxAngleVelocity.isSelected()) {
			panel.chartDataSet.addSeries(model.gyoRawYAxisTimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.gyoRawYAxisTimeSeries);
			model.gyoRawYAxisTimeSeries.clear();
		}
	}

	/**
	 *	Function Info
	 *		euler angle check box state change
	 */
	public void eulerAngleCheckBoxStateChange() {
		if (panel.checkBoxEulerAngle.isSelected()) {
			panel.chartDataSet.addSeries(model.eulerAnglesXAxisTimeSeries);
			panel.chartDataSet.addSeries(model.eulerAnglesYAxisTimeSeries);
			panel.chartDataSet.addSeries(model.eulerAnglesZAxisTimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.eulerAnglesXAxisTimeSeries);
			panel.chartDataSet.removeSeries(model.eulerAnglesYAxisTimeSeries);
			panel.chartDataSet.removeSeries(model.eulerAnglesZAxisTimeSeries);

			model.eulerAnglesXAxisTimeSeries.clear();
			model.eulerAnglesYAxisTimeSeries.clear();
			model.eulerAnglesZAxisTimeSeries.clear();
		}
	}
	
	/**
	 *	Function Info
	 *		acceleration filtered data check box state change
	 */
	public void accFilteredCheckBoxStateChange() {
		if (panel.checkBoxAccelerationFiltered.isSelected()) {
			panel.chartDataSet.addSeries(model.accFilteredXAxisTimeSeries);
			panel.chartDataSet.addSeries(model.accFilteredYAxisTimeSeries);
			panel.chartDataSet.addSeries(model.accFilteredZAxisTimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.accFilteredXAxisTimeSeries);
			panel.chartDataSet.removeSeries(model.accFilteredYAxisTimeSeries);
			panel.chartDataSet.removeSeries(model.accFilteredZAxisTimeSeries);

			model.accFilteredXAxisTimeSeries.clear();
			model.accFilteredYAxisTimeSeries.clear();
			model.accFilteredZAxisTimeSeries.clear();
		}
	}

	/**
	 *	Function Info
	 *		angle velocity filtered data check box state change
	 */
	public void angVelFilteredCheckBoxStateChange() {
		if (panel.checkBoxAngleVelocityFiltered.isSelected()) {
			panel.chartDataSet.addSeries(model.gyoFilteredYAxisTimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.gyoFilteredYAxisTimeSeries);
			model.gyoFilteredYAxisTimeSeries.clear();
		}
	}

	/**
	 *	Function Info
	 *		euler angle filtered data check box state change
	 */
	public void eulerAngleFilteredCheckBoxStateChange() {
		if (panel.checkBoxEulerAngleFiltered.isSelected()) {
			panel.chartDataSet.addSeries(model.eulerAnglesFilteredXAxisTimeSeries);
			panel.chartDataSet.addSeries(model.eulerAnglesFilteredYAxisTimeSeries);
			panel.chartDataSet.addSeries(model.eulerAnglesFilteredZAxisTimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.eulerAnglesFilteredXAxisTimeSeries);
			panel.chartDataSet.removeSeries(model.eulerAnglesFilteredYAxisTimeSeries);
			panel.chartDataSet.removeSeries(model.eulerAnglesFilteredZAxisTimeSeries);

			model.eulerAnglesFilteredXAxisTimeSeries.clear();
			model.eulerAnglesFilteredYAxisTimeSeries.clear();
			model.eulerAnglesFilteredZAxisTimeSeries.clear();
		}
	}
	
	/**
	 *	Function Info
	 *		toe-off heel-hit check box state change
	 */
	public void toeOffHeelHitDetectCheckBoxStateChange() {
		if (panel.checkBoxToeOffHeelHitDetect.isSelected()) {
			panel.chartDataSet.addSeries(model.toeOffHeelHitTimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.toeOffHeelHitTimeSeries);
		}
	}
	
	/**
	 *	Function Info
	 *		debug check box state change
	 */
	public void debugCheckBoxStateChange() {
		if (panel.checkBoxDebug.isSelected()) {
			panel.chartDataSet.addSeries(model.debugPPeakTimeSeries);
			panel.chartDataSet.addSeries(model.debugPGradientTimeSeries);
			panel.chartDataSet.addSeries(model.debugPPeriodTimeSeries);
			panel.chartDataSet.addSeries(model.debugPTotalTimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.debugPPeakTimeSeries);
			panel.chartDataSet.removeSeries(model.debugPGradientTimeSeries);
			panel.chartDataSet.removeSeries(model.debugPPeriodTimeSeries);
			panel.chartDataSet.removeSeries(model.debugPTotalTimeSeries);
		}
	}
	
	/**
	 *	Function Info
	 *		StartToRecord button clicked event handler 
	 */
	public void startToRecordButtonCliced() {

		switch (panel.buttonStartStopRecord.getText()) {
		case "StartRecord":
			setRecordingFlag();
			break;
		case "StopRecord":
			clearRecordingFlag();
			break;
		default:
			break;
		}
	}
	
	public void setRecordingFlag() {
		recordingFlag = true;
		model.setRecordTimeLengthInS(Integer.valueOf(panel.textFieldRecordLength.getText()));
		panel.buttonStartStopRecord.setText("StopRecord");
	}
	
	public void clearRecordingFlag() {
		recordingFlag = false;
		panel.buttonStartStopRecord.setText("StartRecord");
	}

	/**
	 *	Function Info
	 *		SaveToFile button clicked event handler 
	 */
	public void saveToFileButtonCliced() {

		JFileChooser jFileChooser = new JFileChooser();
		int chooseResult = jFileChooser.showSaveDialog(null);
		if (chooseResult == JFileChooser.APPROVE_OPTION) {
		}
		else {
			return;
		}

		File file = jFileChooser.getSelectedFile();
		try {
			boolean createFileResult = file.createNewFile();
			if (false == createFileResult) {
				file.delete();
				file.createNewFile();
			}
			else {
			}

			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (int i = 0; i < panel.chartDataSet.getSeriesCount(); i++) {
				TimeSeries tempSeries = panel.chartDataSet.getSeries(i);
				bufferedWriter.write(tempSeries.getKey().toString() + "\n");
				for (int j = 0; j < tempSeries.getItemCount(); j++) {
					bufferedWriter.write(
							tempSeries.getTimePeriod(j).toString() 
							+ " " + tempSeries.getValue(j) + "\n");
				}
				bufferedWriter.write("\n");
			}

			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *	Func Info:
	 *		detect algorithm update event listen 
	 */
	public void newTOHSPointFound(IMUTOHSDataModel imutohsDataModel) {
		model.toeOffHeelHitTimeSeries.addOrUpdate(imutohsDataModel.time, imutohsDataModel.value.doubleValue());
	}
}
