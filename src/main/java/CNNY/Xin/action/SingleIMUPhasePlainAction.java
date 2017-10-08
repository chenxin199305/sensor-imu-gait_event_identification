package CNNY.Xin.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;

import CNNY.Xin.model.AverageFilter;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.SingleIMUPhasePlainModel;
import CNNY.Xin.view.SingleIMUPhasePlainPanel;

public class SingleIMUPhasePlainAction {

	private SingleIMUPhasePlainModel model;
	private SingleIMUPhasePlainPanel panel;

	private Boolean recordingFlag = true;
	
	private AverageFilter averageFilterGyoX = new AverageFilter(4);
	private AverageFilter averageFilterGyoY = new AverageFilter(4);
	private AverageFilter averageFilterGyoZ = new AverageFilter(4);

	private AverageFilter averageFilterEulerAngleX = new AverageFilter(4);
	private AverageFilter averageFilterEulerAngleY = new AverageFilter(4);
	private AverageFilter averageFilterEulerAngleZ = new AverageFilter(4);
	
	public SingleIMUPhasePlainAction(
			SingleIMUPhasePlainModel singleIMUPhasePlainModel,
			SingleIMUPhasePlainPanel singleIMUPhasePlainPanel) {
	
		this.model = singleIMUPhasePlainModel;
		this.panel = singleIMUPhasePlainPanel;
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
		
		double updateFrequency = 100.0;
		
		// filtering...
		double gyoX = averageFilterGyoX.filter(imuDataModel.GyoRaw[0].doubleValue());
		double gyoY = averageFilterGyoY.filter(imuDataModel.GyoRaw[1].doubleValue());
		double gyoZ = averageFilterGyoZ.filter(imuDataModel.GyoRaw[2].doubleValue());

		double eulerAngleX = averageFilterEulerAngleX.filter(imuDataModel.EulerAngles[0].doubleValue());
		double eulerAngleY = averageFilterEulerAngleY.filter(imuDataModel.EulerAngles[1].doubleValue());
		double eulerAngleZ = averageFilterEulerAngleZ.filter(imuDataModel.EulerAngles[2].doubleValue());
		
		// update data
		if (model.gyoXAxisArray.size() == model.seriesMaximumCount) {
			model.gyoXAxisXYSeries.remove(model.gyoXAxisArray.get(0));
			model.gyoXAxisArray.remove(0);
		}
		if (model.gyoYAxisArray.size() == model.seriesMaximumCount) {
			model.gyoYAxisXYSeries.remove(model.gyoYAxisArray.get(0));
			model.gyoYAxisArray.remove(0);
		}
		if (model.gyoZAxisArray.size() == model.seriesMaximumCount) {
			model.gyoZAxisXYSeries.remove(model.gyoZAxisArray.get(0));
			model.gyoZAxisArray.remove(0);
		}
		
		if (model.eulerAngleXAxisArray.size() == model.seriesMaximumCount) {
			model.eulerAnglesXAxisXYSeries.remove(model.eulerAngleXAxisArray.get(0));
			model.eulerAngleXAxisArray.remove(0);
		}
		if (model.eulerAngleYAxisArray.size() == model.seriesMaximumCount) {
			model.eulerAnglesYAxisXYSeries.remove(model.eulerAngleYAxisArray.get(0));
			model.eulerAngleYAxisArray.remove(0);
		}
		if (model.eulerAngleZAxisArray.size() == model.seriesMaximumCount) {
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
		
	}

	public void checkBoxAngleVelocityStateChange() {
		if (panel.checkBoxAngleVelocity.isSelected()) {
			panel.chartDataSet.addSeries(model.gyoXAxisXYSeries);
			panel.chartDataSet.addSeries(model.gyoYAxisXYSeries);
			panel.chartDataSet.addSeries(model.gyoZAxisXYSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.gyoXAxisXYSeries);
			panel.chartDataSet.removeSeries(model.gyoYAxisXYSeries);
			panel.chartDataSet.removeSeries(model.gyoZAxisXYSeries);

			model.gyoXAxisXYSeries.clear();
			model.gyoYAxisXYSeries.clear();
			model.gyoZAxisXYSeries.clear();
		}
	}

	public void checkBoxEulerAngleStateChange() {
		if (panel.checkBoxEulerAngle.isSelected()) {
			panel.chartDataSet.addSeries(model.eulerAnglesXAxisXYSeries);
//			panel.chartDataSet.addSeries(model.eulerAnglesYAxisXYSeries);
//			panel.chartDataSet.addSeries(model.eulerAnglesZAxisXYSeries);

			panel.lineAndShapeRenderer.setSeriesLinesVisible(0, false);
		}
		else {
			panel.chartDataSet.removeSeries(model.eulerAnglesXAxisXYSeries);
//			panel.chartDataSet.removeSeries(model.eulerAnglesYAxisXYSeries);
//			panel.chartDataSet.removeSeries(model.eulerAnglesZAxisXYSeries);
			
			model.eulerAnglesXAxisXYSeries.clear();
			model.eulerAnglesYAxisXYSeries.clear();
			model.eulerAnglesZAxisXYSeries.clear();
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
