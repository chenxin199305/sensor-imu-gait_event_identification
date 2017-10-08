package CNNY.Xin.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.jfree.data.time.TimeSeries;

import CNNY.Xin.model.FootPressureForceSensorDataDisplayModel;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.view.FootPressureForceSensorDataDisplayPanel;

public class FootPressureForceSensorDataDisplayAction {

	private FootPressureForceSensorDataDisplayModel model;
	private FootPressureForceSensorDataDisplayPanel panel;
	
	private Boolean recordingFlag = true;
	
	public FootPressureForceSensorDataDisplayAction(
			FootPressureForceSensorDataDisplayModel model,
			FootPressureForceSensorDataDisplayPanel panel) {
	
		this.model = model;
		this.panel = panel;
	}
	
	/**
	 *	Func Info:
	 *		imu data update event
	 */
	public void imuDataUpdate(IMUDataModel imuDataModel) {

	}
	
	/**
	 *	Function Info
	 *		acceleration check box state change
	 */
	public void sensor61CheckBoxStateChange() {
		if (panel.checkBoxSensor61.isSelected()) {
			panel.chartDataSet.addSeries(model.sensor61TimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.sensor61TimeSeries);
			model.sensor61TimeSeries.clear();
		}
	}

	/**
	 *	Function Info
	 *		angle velocity check box state change
	 */
	public void sensor62CheckBoxStateChange() {
		if (panel.checkBoxSensor62.isSelected()) {
			panel.chartDataSet.addSeries(model.sensor62TimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.sensor62TimeSeries);
			model.sensor62TimeSeries.clear();
		}
	}

	/**
	 *	Function Info
	 *		euler angle check box state change
	 */
	public void sensor63CheckBoxStateChange() {
		if (panel.checkBoxSensor63.isSelected()) {
			panel.chartDataSet.addSeries(model.sensor63TimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.sensor63TimeSeries);
			model.sensor63TimeSeries.clear();
		}
	}
	
	/**
	 *	Function Info
	 *		acceleration filtered data check box state change
	 */
	public void sensor64CheckBoxStateChange() {
		if (panel.checkBoxSensor64.isSelected()) {
			panel.chartDataSet.addSeries(model.sensor64TimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.sensor64TimeSeries);
			model.sensor64TimeSeries.clear();
		}
	}

	/**
	 *	Function Info
	 *		angle velocity filtered data check box state change
	 */
	public void sensor32CheckBoxStateChange() {
		if (panel.checkBoxSensor32.isSelected()) {
			panel.chartDataSet.addSeries(model.sensor32TimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.sensor32TimeSeries);
			model.sensor32TimeSeries.clear();
		}
	}

	/**
	 *	Function Info
	 *		euler angle filtered data check box state change
	 */
	public void sensor33CheckBoxStateChange() {
		if (panel.checkBoxSensor33.isSelected()) {
			panel.chartDataSet.addSeries(model.sensor33TimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.sensor33TimeSeries);
			model.sensor33TimeSeries.clear();
		}
	}
	
	/**
	 *	Function Info
	 *		toe-off heel-hit check box state change
	 */
	public void toePressureCheckBoxStateChange() {
		if (panel.checkBoxToePressure.isSelected()) {
			panel.chartDataSet.addSeries(model.toePressureTimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.toePressureTimeSeries);
			model.toePressureTimeSeries.clear();
		}
	}
	
	/**
	 *	Function Info
	 *		debug check box state change
	 */
	public void heelPressureCheckBoxStateChange() {
		if (panel.checkBoxHeelPressure.isSelected()) {
			panel.chartDataSet.addSeries(model.heelPressureTimeSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.heelPressureTimeSeries);
			model.heelPressureTimeSeries.clear();
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

}
