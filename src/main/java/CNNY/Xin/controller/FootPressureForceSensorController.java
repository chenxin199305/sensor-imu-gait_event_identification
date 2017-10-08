package CNNY.Xin.controller;

import CNNY.Xin.action.FootPressureForceSensorAction;
import CNNY.Xin.event.FootPressureForceSensorDataUpdateEventListener;
import CNNY.Xin.model.FootPressureForceSensorDataModel;
import CNNY.Xin.model.FootPressureForceSensorModel;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.view.FootPressureForceSensorPanel;

public class FootPressureForceSensorController implements FootPressureForceSensorDataUpdateEventListener {

	public FootPressureForceSensorAction action;
	public FootPressureForceSensorModel model;
	public FootPressureForceSensorPanel panel;
	
	// serial port MVA
	private FootPressureForceSensorSerialPortController serialPortController;
	
	// data display MVA
	private FootPressureForceSensorDataDisplayController dataDisplayController;
	
	/**
	 *	Function Info:
	 *		Initialization 
	 */
	public FootPressureForceSensorController(
			FootPressureForceSensorModel model,
			FootPressureForceSensorPanel panel) {

		this.model = model;
		this.panel = panel;
		this.action = new FootPressureForceSensorAction(this.model, this.panel);

		// serial port
		this.serialPortController = 
				new FootPressureForceSensorSerialPortController(
						model.serialPortModel, 
						panel.serialPortPanel);
		
		this.serialPortController.action.eventManager.addListener(this);
		
		// data display
		this.dataDisplayController = 
				new FootPressureForceSensorDataDisplayController(
						model.dataDisplayModel,
						panel.dataDisplayPanel);
		
		initActionListener();
	}

	/**
	 *	Function Info:
	 *		Initiate Action Listeners 
	 */
	private void initActionListener() {
	}

	/**
	 *	Func Info:
	 *		foot pressure force sensor data update event listen 
	 */
	@Override
	public void footPressureForceSensorDataUpdate(FootPressureForceSensorDataModel footPressureForceSensorDataModel) {

	}

	/**
	 *	Func Info:
	 *		set data info panel recordingFlag to true 
	 */
	public void setDataDisplayRecordingFlag() {
		this.dataDisplayController.setRecordingFlag();
	}

	/**
	 *	Func Info:
	 *		clear data info panel recordingFlag to true 
	 */
	public void clearDataDisplayRecordingFlag() {
		this.dataDisplayController.clearRecordingFlag();
	}
	
}
