package CNNY.Xin.action;

import CNNY.Xin.model.SingleIMUPhasePlainModel;
import CNNY.Xin.view.SingleIMUPhasePlainPanel;

public class SingleIMUPhasePlainAction {

	private SingleIMUPhasePlainModel singleIMUPhasePlainModel;
	private SingleIMUPhasePlainPanel singleIMUPhasePlainPanel;
	
	public SingleIMUPhasePlainAction(
			SingleIMUPhasePlainModel singleIMUPhasePlainModel,
			SingleIMUPhasePlainPanel singleIMUPhasePlainPanel) {
	
		this.singleIMUPhasePlainModel = singleIMUPhasePlainModel;
		this.singleIMUPhasePlainPanel = singleIMUPhasePlainPanel;
	}
}
