package CNNY.Xin.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import CNNY.Xin.action.IMUDataDisplayAction;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.IMUTOHSDataModel;
import CNNY.Xin.model.IMUDataDisplayModel;
import CNNY.Xin.view.IMUDataDisplayPanel;

public class IMUDataDisplayController {

	public IMUDataDisplayAction action;
	public IMUDataDisplayModel model;
	public IMUDataDisplayPanel panel;

	public IMUDataDisplayController(
			IMUDataDisplayModel iMUDataDisplayModel,
			IMUDataDisplayPanel singleIMUDataDisplayPanel) {

		this.model = iMUDataDisplayModel;
		this.panel = singleIMUDataDisplayPanel;
		this.action = new IMUDataDisplayAction(model, panel);

		initActionListener();
	}
	
	private void initActionListener() {

		// check box selection
		panel.checkBoxAcceleration.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.accelerationCheckBoxStateChange();
			}
		});

		panel.checkBoxAngleVelocity.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.angleVelocityCheckBoxStateChange();
			}
		});

		panel.checkBoxEulerAngle.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.eulerAngleCheckBoxStateChange();
			}
		});

		panel.checkBoxAccelerationFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.accFilteredCheckBoxStateChange();
			}
		});

		panel.checkBoxAngleVelocityFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.angVelFilteredCheckBoxStateChange();
			}
		});

		panel.checkBoxEulerAngleFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.eulerAngleFilteredCheckBoxStateChange();
			}
		});

		panel.checkBoxToeOffHeelHitDetect.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.toeOffHeelHitDetectCheckBoxStateChange();
			}
		});

		panel.checkBoxDebug.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.debugCheckBoxStateChange();
			}
		});

		// StartToRecord Button Clicked
		panel.buttonStartStopRecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				action.startToRecordButtonCliced();
			}
		});

		// SaveToFile Button Clicked
		panel.buttonSaveToFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				action.saveToFileButtonCliced();
			}
		});
	}
	
	/**
	 *	Func Info:
	 *		imu data update event
	 */
	public void imuDataUpdate(IMUDataModel imuDataModel) {
		action.imuDataUpdate(imuDataModel);
	}
	
	/**
	 *	Func Info:
	 *		detect algorithm update event listen 
	 */
	public void newTOHSPointFound(IMUTOHSDataModel imutohsDataModel) {
		action.newTOHSPointFound(imutohsDataModel);
	}

	public void setRecordingFlag() {
		action.setRecordingFlag();
	}

	public void clearRecordingFlag() {
		action.clearRecordingFlag();
	}

}
