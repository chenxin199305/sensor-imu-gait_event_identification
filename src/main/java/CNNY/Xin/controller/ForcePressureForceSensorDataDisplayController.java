package CNNY.Xin.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import CNNY.Xin.action.SingleIMUDataDisplayAction;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.SingleIMUDataDisplayModel;
import CNNY.Xin.view.SingleIMUDataDisplayPanel;

public class ForcePressureForceSensorDataDisplayController {

	public SingleIMUDataDisplayAction action;
	public SingleIMUDataDisplayModel model;
	public SingleIMUDataDisplayPanel panel;

	public ForcePressureForceSensorDataDisplayController(
			SingleIMUDataDisplayModel singleIMUDataDisplayModel,
			SingleIMUDataDisplayPanel singleIMUDataDisplayPanel) {

		this.model = singleIMUDataDisplayModel;
		this.panel = singleIMUDataDisplayPanel;
		this.action = new SingleIMUDataDisplayAction(model, panel);

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

	public void setRecordingFlag() {
		action.setRecordingFlag();
	}

	public void clearRecordingFlag() {
		action.clearRecordingFlag();
	}
}
