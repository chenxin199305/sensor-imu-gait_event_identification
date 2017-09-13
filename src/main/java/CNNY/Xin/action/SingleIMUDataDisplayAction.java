package CNNY.Xin.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.jfree.data.time.TimeSeries;

import CNNY.Xin.model.SingleIMUDataDisplayModel;
import CNNY.Xin.view.SingleIMUDataDisplayPanel;

public class SingleIMUDataDisplayAction {

	private SingleIMUDataDisplayModel singleIMUDataDisplayModel;
	private SingleIMUDataDisplayPanel singleIMUDataDisplayPanel;
	
	private Boolean recordingFlag = true;
	
	public SingleIMUDataDisplayAction(
			SingleIMUDataDisplayModel singleIMUDataDisplayModel,
			SingleIMUDataDisplayPanel singleIMUDataDisplayPanel) {
	
		this.singleIMUDataDisplayModel = singleIMUDataDisplayModel;
		this.singleIMUDataDisplayPanel = singleIMUDataDisplayPanel;
	}
	
	/**
	 *	Function Info
	 *		acceleration check box state change
	 */
	public void accelerationCheckBoxStateChange() {
		if (singleIMUDataDisplayPanel.chckbxAcceleration.isSelected()) {
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.accRawXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.accRawYAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.accRawZAxisTimeSeries);
		}
		else {
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.accRawXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.accRawYAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.accRawZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		angle velocity check box state change
	 */
	public void angleVelocityCheckBoxStateChange() {
		if (singleIMUDataDisplayPanel.chckbxAngleVelocity.isSelected()) {
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.gyoRawXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.gyoRawYAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.gyoRawZAxisTimeSeries);
		}
		else {
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.gyoRawXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.gyoRawYAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.gyoRawZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		euler angle check box state change
	 */
	public void eulerAngleCheckBoxStateChange() {
		if (singleIMUDataDisplayPanel.chckbxEulerAngle.isSelected()) {
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.eulerAnglesXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.eulerAnglesYAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.eulerAnglesZAxisTimeSeries);
		}
		else {
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.eulerAnglesXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.eulerAnglesYAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.eulerAnglesZAxisTimeSeries);
		}
	}
	
	/**
	 *	Function Info
	 *		acceleration filtered data check box state change
	 */
	public void accFilteredCheckBoxStateChange() {
		if (singleIMUDataDisplayPanel.chckbxAccFiltered.isSelected()) {
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.accFilteredXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.accFilteredYAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.accFilteredZAxisTimeSeries);
		}
		else {
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.accFilteredXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.accFilteredYAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.accFilteredZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		angle velocity filtered data check box state change
	 */
	public void angVelFilteredCheckBoxStateChange() {
		if (singleIMUDataDisplayPanel.chckbxAngvelFiltered.isSelected()) {
//			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.gyoFilteredXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.gyoFilteredYAxisTimeSeries);
//			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.gyoFilteredZAxisTimeSeries);
		}
		else {
//			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.gyoFilteredXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.gyoFilteredYAxisTimeSeries);
//			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.gyoFilteredZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		euler angle filtered data check box state change
	 */
	public void eulerAngleFilteredCheckBoxStateChange() {
		if (singleIMUDataDisplayPanel.chckbxEulangFiltered.isSelected()) {
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.eulerAnglesFilteredXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.eulerAnglesFilteredYAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.eulerAnglesFilteredZAxisTimeSeries);
		}
		else {
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.eulerAnglesFilteredXAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.eulerAnglesFilteredYAxisTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.eulerAnglesFilteredZAxisTimeSeries);
		}
	}
	
	/**
	 *	Function Info
	 *		toe-off heel-hit check box state change
	 */
	public void toeOffHeelHitDetectCheckBoxStateChange() {
		if (singleIMUDataDisplayPanel.chckbxToeOffHeelHitDetect.isSelected()) {
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.toeOffHeelHitTimeSeries);
		}
		else {
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.toeOffHeelHitTimeSeries);
		}
	}
	
	/**
	 *	Function Info
	 *		debug check box state change
	 */
	public void debugCheckBoxStateChange() {
		if (singleIMUDataDisplayPanel.chckbxDebug.isSelected()) {
//			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.debugPPeakTimeSeries);
//			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.debugPGradientTimeSeries);
//			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.debugPPeriodTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.addSeries(singleIMUDataDisplayModel.debugPTotalTimeSeries);
		}
		else {
//			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.debugPPeakTimeSeries);
//			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.debugPGradientTimeSeries);
//			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.debugPPeriodTimeSeries);
			singleIMUDataDisplayPanel.chartDataSet.removeSeries(singleIMUDataDisplayModel.debugPTotalTimeSeries);
		}
	}
	
	/**
	 *	Function Info
	 *		StartToRecord button clicked event handler 
	 */
	public void startToRecordButtonCliced() {

		switch (singleIMUDataDisplayPanel.btnStartRecord.getText()) {
		case "StartRecord":
			recordingFlag = true;
			singleIMUDataDisplayPanel.btnStartRecord.setText("StopRecord");
			break;
		case "StopRecord":
			recordingFlag = false;
			singleIMUDataDisplayPanel.btnStartRecord.setText("StartRecord");
			break;
		default:
			break;
		}
	}
	
	public void setToRecordState() {
		recordingFlag = true;
		singleIMUDataDisplayPanel.btnStartRecord.setText("StopRecord");
	}
	
	public void setToStopRecordState() {
		recordingFlag = false;
		singleIMUDataDisplayPanel.btnStartRecord.setText("StartRecord");
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

			for (int i = 0; i < singleIMUDataDisplayPanel.chartDataSet.getSeriesCount(); i++) {
				TimeSeries tempTimeSeries = singleIMUDataDisplayPanel.chartDataSet.getSeries(i);
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
}
