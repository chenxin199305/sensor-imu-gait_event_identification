package CNNY.Xin.action;

import CNNY.Xin.model.SingleIMUDataDisplayModel;
import CNNY.Xin.view.SingleIMUDataDisplayPanel;

public class SingleIMUDataDisplayAction {

	private SingleIMUDataDisplayModel singleIMUDataDisplayModel;
	private SingleIMUDataDisplayPanel singleIMUDataDisplayPanel;
	
	public SingleIMUDataDisplayAction(
			SingleIMUDataDisplayModel singleIMUDataDisplayModel,
			SingleIMUDataDisplayPanel singleIMUDataDisplayPanel) {
	
		this.singleIMUDataDisplayModel = singleIMUDataDisplayModel;
		this.singleIMUDataDisplayPanel = singleIMUDataDisplayPanel;
	}
	
}
