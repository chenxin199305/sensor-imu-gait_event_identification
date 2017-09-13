package CNNY.Xin.controller;

import CNNY.Xin.action.SingleIMUPhasePlainAction;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.SingleIMUPhasePlainModel;
import CNNY.Xin.view.SingleIMUPhasePlainPanel;

public class SingleIMUPhasePlainController {

	public SingleIMUPhasePlainAction action;
	public SingleIMUPhasePlainModel model;
	public SingleIMUPhasePlainPanel panel;
	
	public SingleIMUPhasePlainController(
			SingleIMUPhasePlainModel singleIMUPhasePlainModel,
			SingleIMUPhasePlainPanel singleIMUPhasePlainPanel) {
		
		this.model = singleIMUPhasePlainModel;
		this.panel = singleIMUPhasePlainPanel;
		this.action = new SingleIMUPhasePlainAction(model, panel);
		
		initActionListener();
	}

	private void initActionListener() {
		
	}
	
	/**
	 *	Func Info:
	 *		imu data update event
	 */
	public void imuDataUpdate(IMUDataModel imuDataModel) {
		action.imuDataUpdate(imuDataModel);
	}
}
