package CNNY.Xin.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.jfree.chart.ChartFactory;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import CNNY.Xin.model.SingleIMUModel;
import CNNY.Xin.view.SingleIMUDataDisplayPanel;
import CNNY.Xin.view.SingleIMUPanel;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *	Class Info:
 *		IMU Status Action 
 */
public class SingleIMUAction {
	
	private SingleIMUModel singleIMUStatusModel;
	private SingleIMUPanel singleIMUMainPanel;
	private TimeSeriesCollection imuChartTimeSeriesCollection;
	private Boolean recordingFlag = true;

	public SingleIMUAction(
			SingleIMUModel singleIMUStatusModel,
			SingleIMUPanel singleIMUMainPanel) {

		this.singleIMUStatusModel = singleIMUStatusModel;
		this.singleIMUMainPanel = singleIMUMainPanel;

		this.imuChartTimeSeriesCollection = new TimeSeriesCollection();
		this.singleIMUStatusPanel.chartContentVal 	= ChartFactory.createXYLineChart("IMU", "TIME", "VAL", imuChartTimeSeriesCollection);
	}



	/**
	 *	Function Info
	 *		StartToRecord button clicked event handler 
	 */
	public void startToRecordButtonCliced() {

		switch (singleIMUStatusPanel.btnStartRecord.getText()) {
		case "StartRecord":
			recordingFlag = true;
			singleIMUStatusPanel.btnStartRecord.setText("StopRecord");
			break;
		case "StopRecord":
			recordingFlag = false;
			singleIMUStatusPanel.btnStartRecord.setText("StartRecord");
			break;
		default:
			break;
		}
	}
	
	public void setToRecordState() {
		recordingFlag = true;
		singleIMUMainPanel.dataDisplayPanel.btnStartRecord.setText("StopRecord");
	}
	
	public void setToStopRecordState() {
		recordingFlag = false;
		singleIMUMainPanel.dataDisplayPanel.btnStartRecord.setText("StartRecord");
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

			for (int i = 0; i < imuChartTimeSeriesCollection.getSeriesCount(); i++) {
				TimeSeries tempTimeSeries = imuChartTimeSeriesCollection.getSeries(i);
				bufferedWriter.write(tempTimeSeries.getKey().toString() + "\n");
				for (int j = 0; j < tempTimeSeries.getItemCount(); j++) {
					bufferedWriter.write(tempTimeSeries.getTimePeriod(j).toString() + " " + tempTimeSeries.getValue(j) + "\n");
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
	 *	Function Info
	 *		acceleration check box state change
	 */
	public void accelerationCheckBoxStateChange() {
		if (singleIMUMainPanel.dataDisplayPanel.chckbxAcceleration.isSelected()) {
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.accRawXAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.accRawYAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.accRawZAxisTimeSeries);
		}
		else {
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.accRawXAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.accRawYAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.accRawZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		angle velocity check box state change
	 */
	public void angleVelocityCheckBoxStateChange() {
		if (singleIMUMainPanel.dataDisplayPanel.chckbxAngleVelocity.isSelected()) {
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.gyoRawXAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.gyoRawYAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.gyoRawZAxisTimeSeries);
		}
		else {
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.gyoRawXAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.gyoRawYAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.gyoRawZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		euler angle check box state change
	 */
	public void eulerAngleCheckBoxStateChange() {
		if (singleIMUMainPanel.dataDisplayPanel.chckbxEulerAngle.isSelected()) {
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.eulerAnglesXAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.eulerAnglesYAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.eulerAnglesZAxisTimeSeries);
		}
		else {
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.eulerAnglesXAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.eulerAnglesYAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.eulerAnglesZAxisTimeSeries);
		}
	}
	
	/**
	 *	Function Info
	 *		acceleration filtered data check box state change
	 */
	public void accFilteredCheckBoxStateChange() {
		if (singleIMUMainPanel.dataDisplayPanel.chckbxAccFiltered.isSelected()) {
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.accFilteredXAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.accFilteredYAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.accFilteredZAxisTimeSeries);
		}
		else {
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.accFilteredXAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.accFilteredYAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.accFilteredZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		angle velocity filtered data check box state change
	 */
	public void angVelFilteredCheckBoxStateChange() {
		if (singleIMUMainPanel.dataDisplayPanel.chckbxAngvelFiltered.isSelected()) {
//			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.gyoFilteredXAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.gyoFilteredYAxisTimeSeries);
//			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.gyoFilteredZAxisTimeSeries);
		}
		else {
//			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.gyoFilteredXAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.gyoFilteredYAxisTimeSeries);
//			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.gyoFilteredZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		euler angle filtered data check box state change
	 */
	public void eulerAngleFilteredCheckBoxStateChange() {
		if (singleIMUMainPanel.dataDisplayPanel.chckbxEulangFiltered.isSelected()) {
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.eulerAnglesFilteredXAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.eulerAnglesFilteredYAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.eulerAnglesFilteredZAxisTimeSeries);
		}
		else {
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.eulerAnglesFilteredXAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.eulerAnglesFilteredYAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.eulerAnglesFilteredZAxisTimeSeries);
		}
	}
	
	/**
	 *	Function Info
	 *		toe-off heel-hit check box state change
	 */
	public void toeOffHeelHitDetectCheckBoxStateChange() {
		if (singleIMUMainPanel.dataDisplayPanel.chckbxToeOffHeelHitDetect.isSelected()) {
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.toeOffHeelHitTimeSeries);
		}
		else {
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.toeOffHeelHitTimeSeries);
		}
	}
	
	/**
	 *	Function Info
	 *		debug check box state change
	 */
	public void debugCheckBoxStateChange() {
		if (singleIMUMainPanel.dataDisplayPanel.chckbxDebug.isSelected()) {
//			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.debugPPeakTimeSeries);
//			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.debugPGradientTimeSeries);
//			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.debugPPeriodTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.debugPTotalTimeSeries);
		}
		else {
//			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.debugPPeakTimeSeries);
//			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.debugPGradientTimeSeries);
//			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.debugPPeriodTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.debugPTotalTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		refresh the display of imu data 
	 */
	public void refreshIMUDataDisplay() {

		// refresh data display on text area
		singleIMUMainPanel.textAreaContentVal.setText(singleIMUStatusModel.imuDataDecoder.imuDataModel.StringData);

		// refresh data display on chart
		if (recordingFlag == true) {
			singleIMUStatusModel.updateTimeSeries();
		}
		else {
		}
	}

}
