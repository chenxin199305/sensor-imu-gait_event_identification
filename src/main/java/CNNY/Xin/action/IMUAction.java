package CNNY.Xin.action;

import CNNY.Xin.model.IMUModel;
import CNNY.Xin.view.IMUPanel;

/**
 *	Class Info:
 *		IMU Status Action 
 */
public class IMUAction {
	
	private IMUModel singleIMUStatusModel;
	private IMUPanel singleIMUMainPanel;
	
	public IMUAction(
			IMUModel singleIMUStatusModel,
			IMUPanel singleIMUMainPanel) {

		this.singleIMUStatusModel = singleIMUStatusModel;
		this.singleIMUMainPanel = singleIMUMainPanel;

	}

}
