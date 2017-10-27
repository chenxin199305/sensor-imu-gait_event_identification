package CNNY.Xin.action;

import CNNY.Xin.model.IMUDetectAlgorithmModel;
import CNNY.Xin.view.IMUDetectAlgorithmPanel;

public class IMUDetectAlgorithmAction {

	private IMUDetectAlgorithmModel model;
	private IMUDetectAlgorithmPanel panel;
	
	public IMUDetectAlgorithmAction(
			IMUDetectAlgorithmModel model,
			IMUDetectAlgorithmPanel panel) {
		
		this.model = model;
		this.panel = panel;
	}
}
