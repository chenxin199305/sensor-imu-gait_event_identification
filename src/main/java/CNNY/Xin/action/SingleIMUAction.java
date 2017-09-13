package CNNY.Xin.action;

import CNNY.Xin.model.SingleIMUModel;
import CNNY.Xin.view.SingleIMUPanel;

/**
 *	Class Info:
 *		IMU Status Action 
 */
public class SingleIMUAction {
	
	private SingleIMUModel singleIMUStatusModel;
	private SingleIMUPanel singleIMUMainPanel;
	
	public SingleIMUAction(
			SingleIMUModel singleIMUStatusModel,
			SingleIMUPanel singleIMUMainPanel) {

		this.singleIMUStatusModel = singleIMUStatusModel;
		this.singleIMUMainPanel = singleIMUMainPanel;

	}

}
