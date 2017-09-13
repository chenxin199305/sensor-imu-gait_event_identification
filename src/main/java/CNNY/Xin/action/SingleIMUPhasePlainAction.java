package CNNY.Xin.action;

import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.SingleIMUPhasePlainModel;
import CNNY.Xin.view.SingleIMUPhasePlainPanel;

public class SingleIMUPhasePlainAction {

	private SingleIMUPhasePlainModel model;
	private SingleIMUPhasePlainPanel panel;
	
	public SingleIMUPhasePlainAction(
			SingleIMUPhasePlainModel singleIMUPhasePlainModel,
			SingleIMUPhasePlainPanel singleIMUPhasePlainPanel) {
	
		this.model = singleIMUPhasePlainModel;
		this.panel = singleIMUPhasePlainPanel;
	}
	
	/**
	 *	Func Info:
	 *		imu data update event
	 */
	public void imuDataUpdate(IMUDataModel imuDataModel) {
		
	}
	
}
