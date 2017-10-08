package CNNY.Xin.action;

import CNNY.Xin.model.SingleIMUModel;
import CNNY.Xin.view.SingleIMUPanel;

/**
 *	Class Info:
 *		IMU Status Action 
 */
public class FootPressureForceSensorAction {
	
	private SingleIMUModel singleIMUStatusModel;
	private SingleIMUPanel singleIMUMainPanel;
	
	public FootPressureForceSensorAction(
			SingleIMUModel singleIMUStatusModel,
			SingleIMUPanel singleIMUMainPanel) {

		this.singleIMUStatusModel = singleIMUStatusModel;
		this.singleIMUMainPanel = singleIMUMainPanel;

	}

}
