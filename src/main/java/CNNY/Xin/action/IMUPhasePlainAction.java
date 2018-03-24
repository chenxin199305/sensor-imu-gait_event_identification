package CNNY.Xin.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.jfree.data.xy.XYSeries;

import CNNY.Xin.event.IMUPhasePlainEventManager;
import CNNY.Xin.model.AverageFilter;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.IMUPhasePlainModel;
import CNNY.Xin.view.IMUPhasePlainPanel;

public class IMUPhasePlainAction {

	private IMUPhasePlainModel model;
	private IMUPhasePlainPanel panel;

	private Boolean recordingFlag = true;
	private double updateFrequency = 100.0;
	public IMUPhasePlainEventManager eventManager;

	private AverageFilter averageFilterGyoX = new AverageFilter(10);
	private AverageFilter averageFilterGyoY = new AverageFilter(10);
	private AverageFilter averageFilterGyoZ = new AverageFilter(10);

	private AverageFilter averageFilterEulerAngleX = new AverageFilter(10);
	private AverageFilter averageFilterEulerAngleY = new AverageFilter(10);
	private AverageFilter averageFilterEulerAngleZ = new AverageFilter(10);

	public IMUPhasePlainAction(
			IMUPhasePlainModel iMUPhasePlainModel,
			IMUPhasePlainPanel singleIMUPhasePlainPanel) {

		this.model = iMUPhasePlainModel;
		this.panel = singleIMUPhasePlainPanel;

		eventManager = new IMUPhasePlainEventManager();
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

		// filtering...
		double gyoX = averageFilterGyoX.filter(imuDataModel.GyoRaw[0].doubleValue() / 10.0);
		double gyoY = averageFilterGyoY.filter(imuDataModel.GyoRaw[1].doubleValue() / 10.0);
		double gyoZ = averageFilterGyoZ.filter(imuDataModel.GyoRaw[2].doubleValue() / 10.0);

		double eulerAngleX = 0.0;//averageFilterEulerAngleX.filter(imuDataModel.EulerAngles[0].doubleValue() / 10.0);
		double eulerAngleY = 0.0;//averageFilterEulerAngleY.filter(imuDataModel.EulerAngles[1].doubleValue() / 10.0);
		double eulerAngleZ = 0.0;//averageFilterEulerAngleZ.filter(imuDataModel.EulerAngles[2].doubleValue() / 10.0);

		// update data
		if (model.gyoXAxisArray.size() == model.recordCountLength) {
			model.gyoXAxisXYSeries.remove(model.gyoXAxisArray.get(0));
			model.gyoXAxisArray.remove(0);
		}
		if (model.gyoYAxisArray.size() == model.recordCountLength) {
			model.gyoYAxisXYSeries.remove(model.gyoYAxisArray.get(0));
			model.gyoYAxisArray.remove(0);
		}
		if (model.gyoZAxisArray.size() == model.recordCountLength) {
			model.gyoZAxisXYSeries.remove(model.gyoZAxisArray.get(0));
			model.gyoZAxisArray.remove(0);
		}

		if (model.eulerAngleXAxisArray.size() == model.recordCountLength) {
			model.eulerAnglesXAxisXYSeries.remove(model.eulerAngleXAxisArray.get(0));
			model.eulerAngleXAxisArray.remove(0);
		}
		if (model.eulerAngleYAxisArray.size() == model.recordCountLength) {
			model.eulerAnglesYAxisXYSeries.remove(model.eulerAngleYAxisArray.get(0));
			model.eulerAngleYAxisArray.remove(0);
		}
		if (model.eulerAngleZAxisArray.size() == model.recordCountLength) {
			model.eulerAnglesZAxisXYSeries.remove(model.eulerAngleZAxisArray.get(0));
			model.eulerAngleZAxisArray.remove(0);
		}

		model.gyoXAxisArray.add(gyoX);
		model.gyoYAxisArray.add(gyoY);
		model.gyoZAxisArray.add(gyoZ);

		model.eulerAngleXAxisArray.add(eulerAngleX);
		model.eulerAngleYAxisArray.add(eulerAngleY);
		model.eulerAngleZAxisArray.add(eulerAngleZ);

		model.gyoXAxisXYSeries.add(gyoX, updateFrequency * (gyoX - model.LastGyoRaw[0]));
		model.gyoYAxisXYSeries.add(gyoY, updateFrequency * (gyoY - model.LastGyoRaw[1]));
		model.gyoZAxisXYSeries.add(gyoZ, updateFrequency * (gyoZ - model.LastGyoRaw[2]));

		model.eulerAnglesXAxisXYSeries.add(eulerAngleX, updateFrequency * (eulerAngleX - model.LastEulerAngles[0]));
		model.eulerAnglesYAxisXYSeries.add(eulerAngleY, updateFrequency * (eulerAngleY - model.LastEulerAngles[1]));
		model.eulerAnglesZAxisXYSeries.add(eulerAngleZ, updateFrequency * (eulerAngleZ - model.LastEulerAngles[2]));

		model.LastGyoRaw[0] = gyoX;
		model.LastGyoRaw[1] = gyoY;
		model.LastGyoRaw[2] = gyoZ;

		model.LastEulerAngles[0] = eulerAngleX;
		model.LastEulerAngles[1] = eulerAngleY;
		model.LastEulerAngles[2] = eulerAngleZ;

		// 99. check one step is finished or not 
		if (model.gyoYAxisArray.size() > 3) {

			Double lastLastGyroAngleVelocityY = model.gyoYAxisArray.get(model.gyoYAxisArray.size() - 3);
			Double lastGyroAngleVelocityY = model.gyoYAxisArray.get(model.gyoYAxisArray.size() - 2);
			Double gyroAngleVelocityY = model.gyoYAxisArray.get(model.gyoYAxisArray.size() - 1);

			Double lastGyroAngleAccelerationY = updateFrequency * (lastGyroAngleVelocityY - lastLastGyroAngleVelocityY);
			Double gyroAngleAccelerationY = updateFrequency * (gyroAngleVelocityY - lastGyroAngleVelocityY);

			Double lastTheta = Math.atan2(lastGyroAngleAccelerationY, lastGyroAngleVelocityY);
			Double theta = Math.atan2(gyroAngleAccelerationY, gyroAngleVelocityY);

			if (lastTheta > -Math.PI && lastTheta < -Math.PI/2 && theta < Math.PI && theta > Math.PI/2) {
				eventManager.notifyListenersOneStepFinish(imuDataModel.receiveTime);
				
				// one step finish, remove all data on the chart
//				model.gyoYAxisArray.clear();
//				model.gyoYAxisXYSeries.clear();
			}
		}
		else {
		}
	}

	/**
	 *	Func Info:
	 *		Angle Velocity Check Box State Change 
	 */
	public void checkBoxAngleVelocityStateChange() {
		if (panel.checkBoxAngleVelocity.isSelected()) {
			panel.chartDataSet.addSeries(model.gyoYAxisXYSeries);
			panel.lineAndShapeRenderer.setSeriesLinesVisible(0, false);
		}
		else {
			panel.chartDataSet.removeSeries(model.gyoYAxisXYSeries);
			model.gyoYAxisXYSeries.clear();
			model.gyoYAxisArray.clear();
		}
	}

	/**
	 *	Func Info:
	 *		Euler Angle Check Box State Change 
	 */
	public void checkBoxEulerAngleStateChange() {
		if (panel.checkBoxEulerAngle.isSelected()) {
			panel.chartDataSet.addSeries(model.eulerAnglesXAxisXYSeries);
			panel.lineAndShapeRenderer.setSeriesLinesVisible(0, false);
		}
		else {
			panel.chartDataSet.removeSeries(model.eulerAnglesXAxisXYSeries);
			model.eulerAnglesYAxisXYSeries.clear();
			model.eulerAngleYAxisArray.clear();
		}
	}

	/**
	 *	Function Info
	 *		StartToRecord button clicked event handler 
	 */
	public void startToRecordButtonCliced() {

		switch (panel.buttonStartStopRecord.getText()) {
		case "StartRecord":
			recordingFlag = true;
			panel.buttonStartStopRecord.setText("StopRecord");
			break;
		case "StopRecord":
			recordingFlag = false;
			panel.buttonStartStopRecord.setText("StartRecord");
			break;
		default:
			break;
		}
	}

	public void setRecordingFlag() {
		recordingFlag = true;
		model.setRecordCountLength(Integer.valueOf(panel.textFieldRecordLength.getText()));
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
				XYSeries tempSeries = panel.chartDataSet.getSeries(i);
				bufferedWriter.write(tempSeries.getKey().toString() + "\n");
				for (int j = 0; j < tempSeries.getItemCount(); j++) {
					bufferedWriter.write(
							tempSeries.getX(j).toString() 
							+ " " + tempSeries.getY(j) + "\n");
				}
				bufferedWriter.write("\n");
			}

			//---------------------- support information ----------------------
			for (int i = 0; i < model.eulerAngleYAxisArray.size(); i++) {
				bufferedWriter.write(model.eulerAngleYAxisArray.get(i).toString() + "\n");
			}

			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
