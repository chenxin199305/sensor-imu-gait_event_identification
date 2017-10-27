package CNNY.Xin.controller;

import org.jfree.data.time.RegularTimePeriod;

import CNNY.Xin.action.IMUAction;
import CNNY.Xin.event.IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventListener;
import CNNY.Xin.event.IMUDataUpdateEventListener;
import CNNY.Xin.event.IMUPhasePlainEventListener;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.IMUModel;
import CNNY.Xin.model.IMUTOHSDataModel;
import CNNY.Xin.view.IMUPanel;

public class IMUController
implements IMUDataUpdateEventListener, 
IMUPhasePlainEventListener, 
IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventListener {

	public IMUAction action;
	public IMUModel model;
	public IMUPanel panel;

	// serial port MVA
	private IMUSerialPortController serialPortController;

	// data display MVA
	private IMUDataDisplayController dataDisplayController;

	// phase plain MVA
	private IMUPhasePlainController phasePlainController;

	// detect algorithm MVA
	private IMUDetectAlgorithmController detectAlgorithmController;

	/**
	 *	Function Info:
	 *		Initialization 
	 */
	public IMUController(
			IMUModel imuModel,
			IMUPanel imuPanel) {

		this.model = imuModel;
		this.panel = imuPanel;
		this.action = new IMUAction(imuModel, imuPanel);

		// serial port
		this.serialPortController = 
				new IMUSerialPortController(
						imuModel.serialPortModel, 
						imuPanel.serialPortPanel);

		this.serialPortController.action.eventManager.addListener(this);

		// data display
		this.dataDisplayController = 
				new IMUDataDisplayController(
						imuModel.dataDisplayModel,
						imuPanel.dataDisplayPanel);

		// phase plain
		this.phasePlainController = 
				new IMUPhasePlainController(
						imuModel.phasePlainModel, 
						imuPanel.phasePlainPanel);

		this.phasePlainController.action.eventManager.addListener(this);

		// detect algorithm
		this.detectAlgorithmController = 
				new IMUDetectAlgorithmController(
						imuModel.detectAlgorithmModel, 
						imuPanel.detectAlgorithmPanel);
		
		this.detectAlgorithmController.adaptiveTOHSDetectAlgorithmController.action.eventManager.addListener(this);

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
	 *		imu data update event listen 
	 */
	@Override
	public void imuDataUpdate(IMUDataModel imuDataModel) {

		dataDisplayController.imuDataUpdate(imuDataModel);
		phasePlainController.imuDataUpdate(imuDataModel);
		detectAlgorithmController.adaptiveTOHSDetectAlgorithmController.imuDataUpdate(imuDataModel);
	}
	
	/**
	 *	Func Info:
	 *		detect algorithm update event listen 
	 */
	@Override
	public void newTOHSPointFound(IMUTOHSDataModel imutohsDataModel) {
		
		dataDisplayController.newTOHSPointFound(imutohsDataModel);
	}

	/**
	 *	Func Info:
	 *		phase plain event listen 
	 */
	@Override
	public void oneStepFinish(RegularTimePeriod receiveTime) {
		
		detectAlgorithmController.adaptiveTOHSDetectAlgorithmController.oneStepFinish(receiveTime);
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

	/**
	 *	Func Info:
	 *		set phase plain recordingFlag to true 
	 */
	public void setPhasePlainRecordingFlag() {
		this.phasePlainController.setRecordingFlag();
	}

	/**
	 *	Func Info:
	 *		clear phase plain recordingFlag to true 
	 */
	public void clearPhasePlainRecordingFlag() {
		this.phasePlainController.clearRecordingFlag();
	}
}
