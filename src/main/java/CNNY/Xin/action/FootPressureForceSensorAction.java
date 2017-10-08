package CNNY.Xin.action;

import CNNY.Xin.model.FootPressureForceSensorModel;
import CNNY.Xin.view.FootPressureForceSensorPanel;

/**
 *	Class Info:
 *		IMU Status Action 
 */
public class FootPressureForceSensorAction {
	
	private FootPressureForceSensorModel model;
	private FootPressureForceSensorPanel panel;
	
	public FootPressureForceSensorAction(
			FootPressureForceSensorModel model,
			FootPressureForceSensorPanel panel) {

		this.model = model;
		this.panel = panel;

	}

}
