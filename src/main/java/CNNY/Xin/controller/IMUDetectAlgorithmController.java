package CNNY.Xin.controller;

import CNNY.Xin.action.IMUDetectAlgorithmAction;
import CNNY.Xin.model.IMUAdaptiveTOHSDetectAlgorithmModel;
import CNNY.Xin.model.IMUDetectAlgorithmModel;
import CNNY.Xin.view.IMUDetectAlgorithmPanel;
import CNNY.Xin.view.detectAlgorithmView.IMUAdaptiveTOHSDetectAlgorithmPanel;

public class IMUDetectAlgorithmController {

	public IMUDetectAlgorithmModel model;
	public IMUDetectAlgorithmPanel panel;
	public IMUDetectAlgorithmAction action;

	// adaptive tohs detect algorithm
	public IMUAdaptiveTOHSDetectAlgorithmController adaptiveTOHSDetectAlgorithmController;
	private IMUAdaptiveTOHSDetectAlgorithmModel adaptiveTOHSDetectAlgorithmModel;
	private IMUAdaptiveTOHSDetectAlgorithmPanel adaptiveTOHSDetectAlgorithmPanel;

	public IMUDetectAlgorithmController(
			IMUDetectAlgorithmModel model,
			IMUDetectAlgorithmPanel panel) {

		this.model = model;
		this.panel = panel;

		this.action = new IMUDetectAlgorithmAction(model, panel);

		// adaptive tohs detect algorithm
		adaptiveTOHSDetectAlgorithmModel = 
				new IMUAdaptiveTOHSDetectAlgorithmModel();
		adaptiveTOHSDetectAlgorithmPanel =
				new IMUAdaptiveTOHSDetectAlgorithmPanel();
		adaptiveTOHSDetectAlgorithmController = 
				new IMUAdaptiveTOHSDetectAlgorithmController(
						adaptiveTOHSDetectAlgorithmModel, 
						adaptiveTOHSDetectAlgorithmPanel);

		// add sub function panel to tabbedPane
		this.panel.tabbedPane.addTab("1", adaptiveTOHSDetectAlgorithmPanel);
	}
}
