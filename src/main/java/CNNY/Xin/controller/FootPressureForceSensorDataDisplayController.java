package CNNY.Xin.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import CNNY.Xin.action.FootPressureForceSensorDataDisplayAction;
import CNNY.Xin.model.FootPressureForceSensorDataDisplayModel;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.view.FootPressureForceSensorDataDisplayPanel;

public class FootPressureForceSensorDataDisplayController {

	public FootPressureForceSensorDataDisplayAction action;
	public FootPressureForceSensorDataDisplayModel model;
	public FootPressureForceSensorDataDisplayPanel panel;

	public FootPressureForceSensorDataDisplayController(
			FootPressureForceSensorDataDisplayModel model,
			FootPressureForceSensorDataDisplayPanel panel) {

		this.model = model;
		this.panel = panel;
		this.action = new FootPressureForceSensorDataDisplayAction(this.model, this.panel);

		initActionListener();
	}
	
	private void initActionListener() {

		// check box selection
		panel.checkBoxSensor61.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.sensor61CheckBoxStateChange();
			}
		});

		panel.checkBoxSensor62.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.sensor62CheckBoxStateChange();
			}
		});

		panel.checkBoxSensor63.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.sensor63CheckBoxStateChange();
			}
		});

		panel.checkBoxSensor64.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.sensor64CheckBoxStateChange();
			}
		});

		panel.checkBoxSensor32.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.sensor32CheckBoxStateChange();
			}
		});

		panel.checkBoxSensor33.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.sensor33CheckBoxStateChange();
			}
		});

		panel.checkBoxToePressure.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.toePressureCheckBoxStateChange();
			}
		});

		panel.checkBoxHeelPressure.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.heelPressureCheckBoxStateChange();
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
